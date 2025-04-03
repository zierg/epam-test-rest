package com.epam.ivko.test.storage;

import com.epam.ivko.test.storage.entity.City;

import java.util.Collection;
import java.util.Set;

public interface CitiesCache {

    void put(City city);
    void put(Collection<City> cities);

    Set<City> getAll();
}
