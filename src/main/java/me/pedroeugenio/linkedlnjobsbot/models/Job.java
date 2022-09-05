package me.pedroeugenio.linkedlnjobsbot.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.pedroeugenio.encurtadorurl.UrlShortner;

import java.io.IOException;
import java.time.Duration;

@Data
@NoArgsConstructor
public class Job {
    private String title;
    private Duration time;
    private String location;
    private String link;
    private String company;
    private String shortLink;


    public Job(String title, Duration time, String location, String link, String company, String strTime) {
        this.title = title;
        this.time = time;
        this.location = location;
        this.link = link;
        this.strTime = strTime;
        this.company = company;
        try {
            this.shortLink = UrlShortner.get(this.link);
        } catch (IOException ignored) {
        }
    }

    @Override
    public String toString() {
        return "Job{" +
                "title='" + title + '\'' +
                ", time=" + time.toHours() + " horas" +
                ", location='" + location + '\'' +
                ", link='" + link + '\'' +
                ", company='" + company + '\'' +
                ", shortUrl='" + shortLink + '\'' +
                '}';
    }
}
