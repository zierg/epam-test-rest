package com.epam.ivko.test.service;

import com.epam.ivko.test.rest.CityResponseEntity;
import com.epam.ivko.test.storage.CitiesStorage;
import com.epam.ivko.test.storage.entity.City;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("unused")
@Service
public class CitiesService {

    private final CitiesStorage storage;

    public CitiesService(
            CitiesStorage storage
    ) {
        this.storage = storage;
    }

    public List<CityResponseEntity> getCities(GetCitiesParams params) {
        Stream<CityResponseEntity> stream = storage.getAll().stream().map(this::createResponseEntity);

        if (params.isEnhanceWithDensity()) {
            stream = stream.peek(this::enhanceWithDensity);
        }

        return stream.toList();
    }

    private CityResponseEntity createResponseEntity(City city) {
        return new CityResponseEntity(
                city.getName(),
                city.getArea(),
                city.getPopulation()
        );
    }

    private void enhanceWithDensity(CityResponseEntity city) {
        Double density = city.getPopulation() / city.getArea();
        city.setDensity(density);
    }
}
