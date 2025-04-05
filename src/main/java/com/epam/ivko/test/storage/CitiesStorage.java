package com.epam.ivko.test.storage;

import com.epam.ivko.test.entity.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CitiesStorage {

    private final CitiesCache cache;
    private final JsonPersistentStorage jsonStorage;
    private final boolean persistChanges;

    public CitiesStorage(
            CitiesCache cache,
            JsonPersistentStorage jsonStorage,
            @Value("${cities.persist-changes:#{false}}") boolean persistChanges
    ) {
        this.cache = cache;
        this.jsonStorage = jsonStorage;
        this.persistChanges = persistChanges;

        loadCities();
    }

    public void addCity(City city) {
        cache.put(city);

        if (persistChanges) {
            jsonStorage.addCity(city);
        }
    }

    public Collection<City> getAll() {
        return cache.getAll();
    }

    private void loadCities() {
        Collection<City> cities = jsonStorage.getCities();
        cache.put(cities);
    }
}
