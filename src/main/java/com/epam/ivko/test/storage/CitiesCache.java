package com.epam.ivko.test.storage;

import com.epam.ivko.test.entity.City;

import java.util.Collection;

public interface CitiesCache {

    void put(City city);
    void put(Collection<City> cities);

    Collection<City> getAll();
}
