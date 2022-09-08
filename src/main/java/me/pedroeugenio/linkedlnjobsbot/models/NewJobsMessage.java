package me.pedroeugenio.linkedlnjobsbot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewJobsMessage {
    private List<String> start;
    private List<String> end;
    private List<String> jobText;

    public String startAsString(){
        return StringUtils.join(start, "\n");
    }

    public String endAsString(){
        return StringUtils.join(end, "\n");
    }

    public String jobTextAsString(){
        return StringUtils.join(jobText, "\n");
    }

}
