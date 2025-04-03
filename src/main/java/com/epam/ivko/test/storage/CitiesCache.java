package com.epam.ivko.test.storage;

import com.epam.ivko.test.entity.City;

import java.util.Collection;
import java.util.Set;

public interface CitiesCache {

    void put(Collection<City> cities);

    Set<City> getAll();
}
