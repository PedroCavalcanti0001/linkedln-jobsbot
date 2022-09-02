package me.pedroeugenio.linkedlnjobsbot.enums;

public enum MomentFilterEnum {
    ANY(""),
    DAY("r86400"),
    WEEK("r604800"),
    MONTH("r2592000");

    private final String id;

    MomentFilterEnum(String filterId) {
        this.id = filterId;
    }

    public String getId() {
        return id;
    }
}
