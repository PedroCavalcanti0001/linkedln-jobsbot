package me.pedroeugenio.linkedlnjobsbot.enums;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

public enum TimeNameDict {
    ONE_HOUR("hora", 60L),
    SOME_HOURS("horas", 60L),
    ONE_DAY("dia", 60*24L),
    SOME_DAYS("dias",60*24L),
    ONE_WEEK("semana", 60*24*7L),
    SOME_WEEKS("semanas", 60*24*7L),
    ONE_MINUTE("minuto", 1L),
    SOME_MINUTES("minutos", 1L);

    final Long multiplier;
    final String text;
    TimeNameDict(String text, Long multiplier){
        this.text = text;
        this.multiplier = multiplier;
    }

    public String getText() {
        return text;
    }

    public Duration getDuration(Integer time){
        return Duration.ofMinutes(this.multiplier*time);
    }

    public static Optional<TimeNameDict> findByText(String text){
        return Arrays.stream(TimeNameDict.values()).filter(e -> e.text.equals(text)).findFirst();
    }
}
