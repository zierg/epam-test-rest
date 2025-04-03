package com.epam.ivko.test.storage.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class City {

    String name;
    double area;
    int population;
}
