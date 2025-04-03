package com.epam.ivko.test.service;

import com.epam.ivko.test.rest.CityRestEntity;
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

    public List<CityRestEntity> getCities(GetCitiesParams params) {
        Stream<CityRestEntity> stream = storage.getAll().stream().map(this::createResponseEntity);

        stream = processNameContains(params, stream);
        stream = processDensity(params, stream);
        stream = processSorting(params, stream);

        return stream.toList();
    }

    private CityRestEntity createResponseEntity(City city) {
        return new CityRestEntity(
                city.getName(),
                city.getArea(),
                city.getPopulation()
        );
    }

    private Stream<CityRestEntity> processNameContains(
            GetCitiesParams params,
            Stream<CityRestEntity> stream
    ) {
        String nameContains = params.getNameContains();

        if (nameContains == null || nameContains.isEmpty()) {
            return stream;
        }

        return stream.filter(
                city -> city.getName().contains(nameContains)
        );
    }

    private Stream<CityRestEntity> processDensity(
            GetCitiesParams params,
            Stream<CityRestEntity> stream
    ) {
        if (params.isEnhanceWithDensity()) {
            stream = stream.peek(this::enhanceWithDensity);
        }
        return stream;
    }

    private void enhanceWithDensity(CityRestEntity city) {
        Double density = city.getPopulation() / city.getArea();
        city.setDensity(density);
    }

    private Stream<CityRestEntity> processSorting(
            GetCitiesParams params,
            Stream<CityRestEntity> stream
    ) {
        var sortingComparator = params.getSorting().getComparator();

        if (sortingComparator == null) {
            return stream;
        }

        if (params.isSortDescending()) {
            sortingComparator = sortingComparator.reversed();
        }

       return stream.sorted(sortingComparator);
    }
}
