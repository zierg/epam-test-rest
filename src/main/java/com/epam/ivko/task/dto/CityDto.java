package com.epam.ivko.task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
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

    @JsonView(JsonViews.RequestView.class)
    String name;

    @JsonView(JsonViews.RequestView.class)
    Double area;

    @JsonView(JsonViews.RequestView.class)
    Integer population;

    @JsonView(JsonViews.ResponseView.class)
    Double density;

    public CityDto(
            String name,
            Double area,
            Integer population
    ) {
        this(name, area, population, null);
    }
}
