package com.epam.ivko.task.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class GetCitiesParams {

    @Builder.Default
    boolean enhanceWithDensity = false;

    @Builder.Default
    CitiesSorting sorting = CitiesSorting.NONE;

    // does not apply if sorting set to NONE
    @Builder.Default
    boolean sortDescending = false;

    @Builder.Default
    String nameContains = null;
}
