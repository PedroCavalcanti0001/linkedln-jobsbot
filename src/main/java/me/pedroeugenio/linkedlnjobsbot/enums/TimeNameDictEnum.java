package me.pedroeugenio.linkedlnjobsbot.enums;

import lombok.Getter;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

@Getter
public enum TimeNameDictEnum {
    ONE_HOUR("hora", 60L),
    SOME_HOURS("horas", 60L),
    ONE_DAY("dia", 60*24L),
    SOME_DAYS("dias",60*24L),
    ONE_WEEK("semana", 60*24*7L),
    SOME_WEEKS("semanas", 60*24*7L),
    ONE_MINUTE("minuto", 1L),
    SOME_MINUTES("minutos", 1L);

    final private Long multiplier;
    final private String text;

    TimeNameDictEnum(String text, Long multiplier){
        this.text = text;
        this.multiplier = multiplier;
    }

    public Duration getDuration(Integer time){
        return Duration.ofMinutes(this.getMultiplier()*time);
    }

    public static Optional<TimeNameDictEnum> findByText(String text){
        return Arrays.stream(TimeNameDictEnum.values()).filter(e -> e.getText().equals(text)).findFirst();
    }
}
