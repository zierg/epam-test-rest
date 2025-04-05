package com.epam.ivko.test.service;

import com.epam.ivko.test.dto.CityDto;
import lombok.Getter;

import java.util.Comparator;

@Getter
@SuppressWarnings("unused")
public enum CitiesSorting {
    NONE(null),
    NAME(Comparator.comparing(CityDto::getName)),
    POPULATION(Comparator.comparing(CityDto::getPopulation)),
    AREA(Comparator.comparing(CityDto::getArea));

    private final Comparator<CityDto> comparator;

    CitiesSorting(Comparator<CityDto> comparator) {
        this.comparator = comparator;
    }
}
