package com.epam.ivko.test.storage;

import com.epam.ivko.test.entity.City;
import org.springframework.stereotype.Service;

import java.util.Set;

@SuppressWarnings("unused")
@Service
public class CitiesStorage {

    private final CitiesCache cache;
    private final JsonPersistentStorage jsonStorage;

    public CitiesStorage(
            CitiesCache cache,
            JsonPersistentStorage jsonStorage
    ) {
        this.cache = cache;
        this.jsonStorage = jsonStorage;

        loadCities();
    }

    public Set<City> getAll() {
        return cache.getAll();
    }

    private void loadCities() {
        Set<City> cities = jsonStorage.getCities();
        cache.put(cities);
    }
}
