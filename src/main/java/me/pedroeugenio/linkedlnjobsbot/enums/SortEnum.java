package me.pedroeugenio.linkedlnjobsbot.enums;

public enum SortEnum {
    RELEVANCE("R"),
    TIME("DD");

    final String text;

    SortEnum(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
