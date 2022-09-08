package me.pedroeugenio.linkedlnjobsbot.models;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class JobsFilter {
    List<String> keywords;

    public String keywordsAsStr(){
        return StringUtils.join(keywords);
    }
}
