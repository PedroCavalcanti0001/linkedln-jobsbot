package me.pedroeugenio.linkedlnjobsbot.enums;

import lombok.Getter;

@Getter
public enum MomentFilterEnum {
    ANY(""),
    DAY("r86400"),
    WEEK("r604800"),
    MONTHLY("r2592000");

    private final String filterId;

    MomentFilterEnum(String filterId) {
        this.filterId = filterId;
    }
}
