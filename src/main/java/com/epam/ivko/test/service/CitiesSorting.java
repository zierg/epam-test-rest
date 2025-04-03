package com.epam.ivko.test.service;

import com.epam.ivko.test.rest.CityRestEntity;
import lombok.Getter;

import java.util.Comparator;

@Getter
@SuppressWarnings("unused")
public enum CitiesSorting {
    NONE(null),
    NAME(Comparator.comparing(CityRestEntity::getName)),
    POPULATION(Comparator.comparing(CityRestEntity::getPopulation)),
    AREA(Comparator.comparing(CityRestEntity::getArea));

    private final Comparator<CityRestEntity> comparator;

    CitiesSorting(Comparator<CityRestEntity> comparator) {
        this.comparator = comparator;
    }
}
