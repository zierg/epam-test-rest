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
        Stream<CityDto> stream = storage.getCities(params).stream().map(cityMapper::toDto);

        stream = processDensity(params, stream);

        return stream.toList();
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
}
