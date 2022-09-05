package me.pedroeugenio.linkedlnjobsbot.enums;

import lombok.Getter;

@Getter
public enum SortEnum {
    RELEVANCE("R"),
    TIME("DD");

    final String text;

    SortEnum(String text){
        this.text = text;
    }
}
