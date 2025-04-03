package com.epam.ivko.test.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityDto {

    String name;
    double area;
    int population;
    Double density;

    public CityDto(
            String name,
            double area,
            int population
    ) {
        this(name, area, population, null);
    }
}
