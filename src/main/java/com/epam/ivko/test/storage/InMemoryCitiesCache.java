package com.epam.ivko.test.storage;

import com.epam.ivko.test.entity.City;
import org.springframework.stereotype.Component;

import java.util.*;

@SuppressWarnings("unused")
@Component
public class InMemoryCitiesCache implements CitiesCache {

    private final Map<String, City> cities = new HashMap<>();  // key - city.name

    @Override
    public void put(City city) {
        if (cities.containsKey(city.getName())) {
            throw new IncorrectCityException("City already exists");
        }

        cities.put(city.getName(), city);
    }

    @Override
    public void put(Collection<City> cities) {
        cities.forEach(this::put);
    }

    @Override
    public Collection<City> getAll() {
        return cities.values();
    }
}
