package com.epam.ivko.test.service;

import com.epam.ivko.test.rest.CityResponseEntity;
import lombok.Getter;

import java.util.Comparator;

@Getter
@SuppressWarnings("unused")
public enum CitiesSorting {
    NONE(null),
    NAME(Comparator.comparing(CityResponseEntity::getName)),
    POPULATION(Comparator.comparing(CityResponseEntity::getPopulation)),
    AREA(Comparator.comparing(CityResponseEntity::getArea));

    private final Comparator<CityResponseEntity> comparator;

    CitiesSorting(Comparator<CityResponseEntity> comparator) {
        this.comparator = comparator;
    }
}
