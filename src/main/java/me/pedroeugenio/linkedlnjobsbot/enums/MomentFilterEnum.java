package me.pedroeugenio.linkedlnjobsbot.enums;

public enum MomentFilterEnum {
    ANY(""),
    DAY("r86400"),
    WEEK("r604800"),
    MONTH("r2592000");

    private final String filterId;

    MomentFilterEnum(String filterId) {
        this.filterId = filterId;
    }

    public String getFilterId() {
        return filterId;
    }
}
