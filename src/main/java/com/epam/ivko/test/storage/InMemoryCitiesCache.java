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
    private final Set<String> cityNames = new HashSet<>();

    public InMemoryCitiesCache() {
        this.cities = new HashSet<>();
    }

    @Override
    public void put(City city) {
        if (cityNames.contains(city.getName())) {
            throw new IncorrectCityException("City already exists");
        }

        cities.add(city);
        cityNames.add(city.getName());
    }

    @Override
    public void put(Collection<City> cities) {
        cities.forEach(this::put);
    }

    @Override
    public Set<City> getAll() {
        return cities;
    }
}
