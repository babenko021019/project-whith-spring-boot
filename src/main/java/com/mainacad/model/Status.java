package com.mainacad.model;

import lombok.Getter;

@Getter
public enum Status {

    OPEN("open"),
    TO_BE_CLOSED("tobeClosed"),
    CLOSED("closed");

    private final String name;

    Status(String name) {
        this.name = name;
    }
}
