package com.epam.ivko.test.storage;

import com.epam.ivko.test.storage.entity.City;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
@Component
public class InMemoryCitiesCache implements CitiesCache {

    private final Set<City> cities;

    public InMemoryCitiesCache() {
        this.cities = new HashSet<>();
    }

    @Override
    public void put(Collection<City> cities) {
        this.cities.addAll(cities);
    }

    @Override
    public Set<City> getAll() {
        return this.cities;
    }
}
