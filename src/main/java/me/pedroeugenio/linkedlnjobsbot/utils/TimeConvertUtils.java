package me.pedroeugenio.linkedlnjobsbot.utils;

import me.pedroeugenio.linkedlnjobsbot.enums.TimeNameDict;

import java.time.Duration;
import java.util.Optional;

public class TimeConvertUtils {

    public static Duration strTimeToDuration(String str) {
        String[] split = str.split(" ");
        if (split.length == 3) {
            String time = split[1];
            String measure = split[2];
            final Optional<TimeNameDict> parsedMeasure = TimeNameDict.findByText(measure);
            if (parsedMeasure.isPresent())
                return parsedMeasure.get().getDuration(Integer.parseInt(time));
        }
        return Duration.ZERO;
    }
}
