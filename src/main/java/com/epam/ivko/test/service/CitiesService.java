package com.epam.ivko.test.service;

import com.epam.ivko.test.dto.CityDto;
import com.epam.ivko.test.mapping.CityMapper;
import com.epam.ivko.test.storage.CitiesStorage;
import com.epam.ivko.test.entity.City;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CitiesService {

    private final CitiesStorage storage;
    private final CityMapper cityMapper;

    public CitiesService(
            CitiesStorage storage,
            CityMapper cityMapper
    ) {
        this.storage = storage;
        this.cityMapper = cityMapper;
    }

    public void addCity(CityDto cityDto) {
        City city = cityMapper.fromDto(cityDto);
        storage.addCity(city);
    }

    public List<CityDto> getCities(GetCitiesParams params) {
        Stream<CityDto> stream = storage.getAll().stream().map(cityMapper::toDto);

        stream = processNameContains(params, stream);
        stream = processDensity(params, stream);
        stream = processSorting(params, stream);

        return stream.toList();
    }

    private Stream<CityDto> processNameContains(
            GetCitiesParams params,
            Stream<CityDto> stream
    ) {
        String nameContains = params.getNameContains();

        if (nameContains == null || nameContains.isEmpty()) {
            return stream;
        }

        String nameContainsLower = nameContains.toLowerCase();

        return stream.filter(
                city -> city.getName().toLowerCase().contains(nameContainsLower)
        );
    }

    private Stream<CityDto> processDensity(
            GetCitiesParams params,
            Stream<CityDto> stream
    ) {
        if (params.isEnhanceWithDensity()) {
            stream = stream.peek(this::enhanceWithDensity);
        }
        return stream;
    }

    private void enhanceWithDensity(CityDto city) {
        Double density = city.getPopulation() / city.getArea();
        city.setDensity(density);
    }

    private Stream<CityDto> processSorting(
            GetCitiesParams params,
            Stream<CityDto> stream
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
