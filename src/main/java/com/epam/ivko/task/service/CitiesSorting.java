package com.epam.ivko.task.service;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public enum CitiesSorting {
    NONE(null),
    NAME("name"),
    POPULATION("population"),
    AREA("area");

    private final String fieldName;

    CitiesSorting(String fieldName) {
        this.fieldName = fieldName;
    }
}
