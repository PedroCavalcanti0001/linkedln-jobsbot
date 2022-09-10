package me.pedroeugenio.linkedlnjobsbot.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.pedroeugenio.encurtadorurl.UrlShortner;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Job {
    private String title;
    private String strTime;
    private LocalDateTime time;
    private String location;
    private String link;
    private String company;
    private String shortLink;
    private Long jobId;

    public Job(String title, LocalDateTime time, String location, String link, String company, String strTime, Long jobId) {
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
        this.jobId = jobId;
    }

    public Duration getDiffInDuration(){
        return Duration.between(time, LocalDateTime.now());
    }
}
