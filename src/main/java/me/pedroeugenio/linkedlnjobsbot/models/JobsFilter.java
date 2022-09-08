package me.pedroeugenio.linkedlnjobsbot.models;

import lombok.Data;

import java.util.List;

@Data
public class JobsFilter {
    List<String> keywords;

    public String keywordsAsStr(){
        return String.join(" ", keywords);
    }
}
