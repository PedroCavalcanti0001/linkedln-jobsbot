package me.pedroeugenio.linkedlnjobsbot.services;

import com.google.common.collect.Lists;
import me.pedroeugenio.linkedlnjobsbot.enums.MomentFilterEnum;
import me.pedroeugenio.linkedlnjobsbot.enums.SortEnum;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.utils.TimeConvertUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LinkedlnBotService {
    private static final Logger LOGGER = LogManager.getLogger(LinkedlnBotService.class.getName());
    private final static String BASE_URL = "https://www.linkedin.com/jobs/search";

    private Document getPageDocument(String url) throws IOException {
        Connection connection = Jsoup.connect(url).header("accept-language", "pt-BR,pt;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        return connection.get();
    }

    private Job parseToJob(Element item) {
        String link = item.select(".base-card__full-link").attr("href");
        String title = item.getElementsByClass("base-search-card__title").text();
        String time = item.getElementsByClass("job-search-card__listdate--new").text();
        String location = item.getElementsByClass("job-search-card__location").text();
        String company = item.getElementsByClass("base-search-card__subtitle").text();
        return new Job(title, TimeConvertUtils.strTimeToDuration(time), location, link, company);
    }

    private Elements getJobsElements(Document document) {
        return document.select("ul.jobs-search__results-list > li");
    }

    public List<Job> getAllJobList(MomentFilterEnum moment, int timeToFilter, String location, SortEnum sort) {
        try {
            String url = makeUrl(moment, location, sort);
            Document pageDocument = getPageDocument(url);
            Elements allElementJobs = getJobsElements(pageDocument);
            List<Job> allJobs = allElementJobs.stream().map(this::parseToJob).collect(Collectors.toList());
            return filterByTime(allJobs, timeToFilter);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Lists.newLinkedList();
    }

    private List<Job> filterByTime(List<Job> jobs, Integer timeToFilter) {
        if (!Objects.isNull(timeToFilter))
            return jobs.stream().filter(e -> e.getTime().toMinutes() <= timeToFilter).collect(Collectors.toList());
        return jobs;
    }

    private String makeUrl(MomentFilterEnum moment, String location, SortEnum sort) {
        try {
            String filter = loadFilter();
            String params = "?f_WT=2&keywords="
                    .concat(filter)
                    .concat("&location=")
                    .concat(location)
                    .concat("&refresh=true")
                    .concat("&sortBy=")
                    .concat(sort.getText());
            if (!moment.equals(MomentFilterEnum.ANY))
                params = params.concat("&f_TPR=").concat(moment.getFilterId());
            return BASE_URL.concat(params);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    private String loadFilter() throws IOException {
        File file = new File("filtro.txt");
        if (!file.exists())
            createNewFilterFile(file.getAbsolutePath());
        return URLEncoder.encode(new String(Files.readAllBytes(Paths.get(file.getAbsolutePath()))), StandardCharsets.UTF_8.toString());
    }

    private void createNewFilterFile(String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write("Java developer");
        fileWriter.close();
    }

}
