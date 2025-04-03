package com.epam.ivko.test.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityRestEntity {

    String name;
    double area;
    int population;
    Double density;

    public CityRestEntity(
            String name,
            double area,
            int population
    ) {
        this(name, area, population, null);
    }
}
