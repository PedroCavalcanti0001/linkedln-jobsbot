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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JobsSearch {
    private Optional<Document> getPageDocument(String url) {
        try {
            Connection.Response response = Jsoup.connect(url)
                    .userAgent(JobsConstants.USER_AGENT)
                    .method(Connection.Method.GET)
                    .headers(JobsConstants.HEADERS)
                    .execute();
            return Optional.of(response.parse());
        } catch (IOException e) {
            JobsConstants.LOGGER.error("Ocorreu um erro ao tentar se conectar ao linkedln ->", e);
        }
        return Optional.empty();
    }

    private Job parseToJob(Element item) {
        String link = item.select(".base-card__full-link").attr("href");
        if(link.isEmpty())
            link = item.getElementsByTag("a").attr("href");
        String title = item.getElementsByClass("base-search-card__title").text();
        String time = item.getElementsByClass("job-search-card__listdate--new").text();
        String location = item.getElementsByClass("job-search-card__location").text();
        String company = item.getElementsByClass("base-search-card__subtitle").text();
        return new Job(title, TimeUtils.strTimeToDuration(time), location, link, company, time);
    }

    private Elements getJobsElements(Document document) {
        return document.select("ul.jobs-search__results-list > li");
    }

    public List<Job> getAllJobList() {
        String url = makeUrl();
        Optional<Document> pageDocument = getPageDocument(url);
        if(pageDocument.isPresent()) {
            Elements allElementJobs = getJobsElements(pageDocument.get());
            List<Job> allJobs = allElementJobs.stream().map(this::parseToJob).collect(Collectors.toList());
            return JobsFilterConfig.performFilters(allJobs);
        }
        return Collections.emptyList();
    }

    private String makeUrl() {
        try {
            String filter = JobsFilterConfig.JOBS_FILTER.keywordsAsStr().replace("'", "\"");
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
            JobsConstants.LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
