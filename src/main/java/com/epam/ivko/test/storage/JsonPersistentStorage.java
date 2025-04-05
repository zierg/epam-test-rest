package com.epam.ivko.test.storage;

import com.epam.ivko.test.entity.City;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class JsonPersistentStorage {

    private final String filePath;
    private final String additionalCitiesFilePath;
    private final ObjectMapper mapper;

    public JsonPersistentStorage(
            @Value("${cities.source-file}") String filePath,
            @Value("${cities.additional-source-file:#{null}}") String additionalCitiesFilePath,
            ObjectMapper mapper
    ) {
        this.filePath = filePath;
        this.additionalCitiesFilePath = additionalCitiesFilePath;
        this.mapper = mapper;
    }

    public void addCity(City city) {
        Set<City> cities = new HashSet<>(loadAdditionalCities());
        cities.add(city);

        try {
            mapper.writeValue(new File(additionalCitiesFilePath), cities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<City> getCities() {
        Set<City> cities = new HashSet<>(loadDefaultCities());
        Set<City> additionalCities = loadAdditionalCities();

        cities.addAll(additionalCities);
        return cities;
    }

    private Set<City> loadAdditionalCities() {
        if (additionalCitiesFilePath == null) {
            return Collections.emptySet();
        }

        File file = new File(additionalCitiesFilePath);

        if (!file.exists()) {
            return Collections.emptySet();
        }

        return loadCitiesFromFile(file);
    }

    private Set<City> loadDefaultCities() {
        try {
            File file = ResourceUtils.getFile("classpath:" + filePath);
            return loadCitiesFromFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<City> loadCitiesFromFile(File file) {
        try {
            return mapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
