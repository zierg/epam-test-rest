package com.epam.ivko.task.service;

import com.epam.ivko.task.dto.CityDto;
import com.epam.ivko.task.entity.City;
import com.epam.ivko.task.mapping.CityMapper;
import com.epam.ivko.task.storage.CitiesStorage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CitiesService {

    private final CitiesStorage storage;
    private final CityMapper cityMapper;
    private final DensityCalculator densityEnhancer;

    public CitiesService(
            CitiesStorage storage,
            CityMapper cityMapper,
            DensityCalculator densityEnhancer
    ) {
        this.storage = storage;
        this.cityMapper = cityMapper;
        this.densityEnhancer = densityEnhancer;
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
            stream = stream.peek(
                    dto -> dto.setDensity(densityEnhancer.calculateDensity(
                            dto.getPopulation(),
                            dto.getArea()
                    ))
            );
        }
        return stream;
    }
}
