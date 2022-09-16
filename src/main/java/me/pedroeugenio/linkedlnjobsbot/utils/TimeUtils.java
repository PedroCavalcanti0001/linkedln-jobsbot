package me.pedroeugenio.linkedlnjobsbot.utils;

import me.pedroeugenio.linkedlnjobsbot.config.AppConfig;
import me.pedroeugenio.linkedlnjobsbot.enums.TimeNameDictEnum;
import me.pedroeugenio.linkedlnjobsbot.models.Properties;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class TimeUtils {
    private static final Properties PROPERTIES = AppConfig.getSingleton();

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

    public static Boolean isWeekend(LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
                localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static String formattedTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static String nextSearchStrTime(LocalDateTime localDateTime) {
        Pair<LocalDateTime, LocalDateTime> interval = PROPERTIES.getInterval();
        LocalDateTime end = interval.getRight();
        LocalDateTime nextRuntime = getNextRuntime(PROPERTIES.getTaskInterval(), localDateTime);
        if (nextRuntime.isAfter(end)) {
            Optional<String> optional = getNextSearchStrAnotherDay(localDateTime, interval);
            if (optional.isPresent())
                return optional.get();
        }
        return "as ".concat(formattedTime(nextRuntime));
    }

    @NotNull
    private static Optional<String> getNextSearchStrAnotherDay(LocalDateTime localDateTime, Pair<LocalDateTime,
            LocalDateTime> interval) {
        LocalDateTime start = interval.getLeft();
        LocalDateTime tomorrow = start.plusDays(1);
        if ((PROPERTIES.getExcludeWeekends() && isWeekend(tomorrow))) {
            String time = formattedTime(start.withMinute(localDateTime.getMinute()));
            return Optional.of("segunda as ".concat(time));
        } else if (PROPERTIES.getAllowTimeInterval()) {
            String time = formattedTime(start.withMinute(localDateTime.getMinute()));
            return Optional.of("amanhã as ".concat(time));
        }
        return Optional.empty();
    }

    @NotNull
    private static LocalDateTime getNextRuntime(Integer minutes, LocalDateTime localDateTime) {
        return localDateTime.plus(minutes, ChronoUnit.MINUTES);
    }
}
