package me.pedroeugenio.linkedlnjobsbot.scraper;

import me.pedroeugenio.linkedlnjobsbot.enums.MomentFilterEnum;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.utils.TimeUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static me.pedroeugenio.linkedlnjobsbot.scraper.JobsConstants.LOGGER;

public class JobsSearch {
    private Optional<Document> getPageDocument(String url) throws IOException {
        try {
            Connection.Response response = Jsoup.connect(url)
                    .userAgent(JobsConstants.USER_AGENT)
                    .method(Connection.Method.GET)
                    .headers(JobsConstants.HEADERS)
                    .execute();
            return Optional.of(response.parse());
        } catch (IOException e) {
            LOGGER.error("Ocorreu um erro ao tentar se conectar ao linkedln -> "+e.getMessage());
            throw e;
        }
    }

    private Job parseToJob(Element item) {
        try {
            String link = item.select(".base-card__full-link").attr("href");
            if (link.isEmpty())
                link = item.getElementsByTag("a").attr("href");
            String title = item.getElementsByClass("base-search-card__title").text();
            String time = item.getElementsByClass("job-search-card__listdate--new").text();
            String location = item.getElementsByClass("job-search-card__location").text();
            String company = item.getElementsByClass("base-search-card__subtitle").text();
            long jobId = 0L;
            try {
                jobId = Long.parseLong(
                        item.getElementsByClass("base-card").attr("data-entity-urn").replace("urn:li:jobPosting:", ""));
            } catch (Exception ignored) {

            }
            Duration duration = TimeUtils.strTimeToDuration(time);
            LocalDateTime localDateTime = TimeUtils.getTimeFromDuration(duration);
            return new Job(title, localDateTime, location, link, company, time, jobId);
        } catch (Exception ex) {
            LOGGER.error("Ocorreu um erro ao tentar parsear job->", ex);
            throw ex;
        }
    }

    private Elements getJobsElements(Document document) {
        return document.select("ul.jobs-search__results-list > li");
    }

    public List<Job> getAllJobList() {
        String url = makeUrl();
        Optional<Document> pageDocument;
        while (true) {
            try {
                pageDocument = getPageDocument(url);
                if (pageDocument.isPresent()) {
                    LOGGER.info("Sucesso ao buscar jobs.");
                    Elements allElementJobs = getJobsElements(pageDocument.get());
                    LOGGER.info("Convertendo elementos html de cada job em objetos...");
                    List<Job> allJobs = allElementJobs.stream().map(this::parseToJob).collect(Collectors.toList());
                    return JobsFilterConfig.performFilters(allJobs);
                }

            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private String makeUrl() {
        try {
            String filter = JobsFilterConfig.CURRENT_FILTER.replace("\n", " ");
            MomentFilterEnum momentFilterEnum = JobsConstants.PROPERTIES.getMoment();
            String params = "";
            if (!momentFilterEnum.equals(MomentFilterEnum.ANY))
                params = params.concat("&f_TPR=").concat(momentFilterEnum.getFilterId());
            params = params
                    .concat("&f_WT=2&keywords=").concat(filter)
                    .concat("&location=").concat(JobsConstants.PROPERTIES.getLocation())
                    .concat("&refresh=true")
                    .concat("&sortBy=").concat(JobsConstants.SORT.getText())
                    .concat("&geoId=").concat(String.valueOf(JobsConstants.PROPERTIES.getGeoId()))
                    .concat("&pageNum=0")
                    .concat("&position=1");
            String urlStr = JobsConstants.BASE_URL.concat(params);
            String decodedURL = URLDecoder.decode(urlStr, "UTF-8");
            URL url = new URL(decodedURL);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            return uri.toURL().toString();
        } catch (IOException | URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
