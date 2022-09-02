package me.pedroeugenio.linkedlnjobsbot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    private String title;
    private Duration time;
    private String location;
    private String link;
    private String company;

    @Override
    public String toString() {
        return "Job{" +
                "title='" + title + '\'' +
                ", time=" + time.toHours() + " horas" +
                ", location='" + location + '\'' +
                ", link='" + link + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
