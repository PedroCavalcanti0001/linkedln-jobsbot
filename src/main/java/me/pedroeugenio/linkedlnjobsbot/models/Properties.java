package me.pedroeugenio.linkedlnjobsbot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Properties {
    Integer interval = 60;
    String order = "TIME";
    String botToken;
    Long botChannel;
}
