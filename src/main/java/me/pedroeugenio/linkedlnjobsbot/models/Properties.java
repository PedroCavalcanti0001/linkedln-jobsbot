package me.pedroeugenio.linkedlnjobsbot.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.pedroeugenio.linkedlnjobsbot.enums.MomentFilterEnum;

@Data
@NoArgsConstructor
public class Properties {
    Integer taskInterval;
    Integer jobsInterval;
    String order;
    String botToken;
    Long botChannel;
    String location;
    MomentFilterEnum moment;
    Long geoId;
    Boolean allowPromoted;
}
