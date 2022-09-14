package me.pedroeugenio.linkedlnjobsbot.utils;

import me.pedroeugenio.linkedlnjobsbot.enums.TimeNameDictEnum;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;

public class TimeUtils {

    public static Duration strTimeToDuration(String str) {
        String[] split = str.split(" ");
        if (split.length == 3) {
            String time = split[1];
            String measure = split[2];
            final Optional<TimeNameDictEnum> parsedMeasure = TimeNameDictEnum.findByText(measure);
            if (parsedMeasure.isPresent())
                return parsedMeasure.get().getDuration(Integer.parseInt(time));
        }
        return Duration.ZERO;
    }

    public static LocalDateTime getTimeFromDuration(Duration duration) {
        return LocalDateTime.now().minus(duration);
    }

    public static Duration getTimeDifference(LocalDateTime time){
        return Duration.between(time, LocalDateTime.now());
    }

    public static LocalDateTime parseTimeFromInterval(String str) {
        LocalDateTime now = LocalDateTime.now();
        String[] split = str.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hour, minute);
    }

    public static Boolean isWeekend(LocalDateTime localDateTime){
        return localDateTime.getDayOfMonth() == DayOfWeek.SATURDAY.getValue() ||
                localDateTime.getDayOfMonth() == DayOfWeek.SUNDAY.getValue();
    }

    public static String formattedTime(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
