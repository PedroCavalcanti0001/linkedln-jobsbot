package me.pedroeugenio.linkedlnjobsbot.services;

import me.pedroeugenio.linkedlnjobsbot.enums.MomentFilterEnum;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class LinkedlnBotService {
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
        return new Job(title, strTimeToDuration(time), location, link, company);
    }

    private Elements getJobsElements(Document document) {
        return document.select("ul.jobs-search__results-list > li");
    }

    private Duration strTimeToDuration(String str) {
        String[] split = str.split(" ");
        if (split.length == 3) {
            String time = split[1];
            String measure = split[2];
            final String oneHour = "hora";
            final String someHours = "horas";
            final String oneDay = "dia";
            final String someDays = "dias";
            final String oneWeek = "semana";
            final String someWeeks = "semanas";
            final String oneMinute = "minuto";
            final String someMinutes = "minutos";
            int timeInt = Integer.parseInt(time);
            switch (measure) {
                case oneHour:
                case someHours:
                    return Duration.ofHours(timeInt);
                case oneDay:
                case someDays:
                    final int daysToHours = timeInt * 24;
                    return Duration.ofHours(daysToHours);
                case oneWeek:
                case someWeeks:
                    final int weeksToHours = timeInt * 7 * 24;
                    return Duration.ofHours(weeksToHours);
                case oneMinute:
                case someMinutes:
                    return Duration.ofMinutes(timeInt);
                default:
                    throw new IllegalArgumentException("Não foi possivel converter o tempo da vaga.");
            }
        }
        return Duration.ZERO;
    }

    public List<Job> getJobList(MomentFilterEnum moment) {
        Job job = new Job();
        try {
            String url = makeUrl(moment);
            Document pageDocument = null;
            pageDocument = getPageDocument(url);
            Elements allJobs = getJobsElements(pageDocument);
            return allJobs.stream().map(this::parseToJob).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Ocorreu uma mapear a lista de jobs", e);
        }
    }


    private String makeUrl(MomentFilterEnum moment) {
        try {
            String filter = loadFilter();
            String params = "?f_WT=2&geoId=106057199&keywords="
                    .concat(filter)
                    .concat("&location=Brasil&refresh=true");
            if (!moment.equals(MomentFilterEnum.ANY))
                params = params.concat("&f_TPR=").concat(moment.getId());

            return BASE_URL.concat(params);
        } catch (IOException e) {
            throw new IllegalArgumentException("Ocorreu um erro ao criar URL ->", e);
        }
    }

    private String loadFilter() throws IOException {
        return URLEncoder.encode(new String(Files.readAllBytes(Paths.get("filtro.txt"))), StandardCharsets.UTF_8.toString());
    }

}
