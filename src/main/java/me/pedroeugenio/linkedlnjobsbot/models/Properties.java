package me.pedroeugenio.linkedlnjobsbot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Properties {
    Integer taskInterval;
    Integer jobsInterval;
    String order;
    String botToken;
    Long botChannel;
    String location;
}
