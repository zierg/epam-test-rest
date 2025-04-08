package com.epam.ivko.task.mapping;

import com.epam.ivko.task.dto.CityDto;
import com.epam.ivko.task.entity.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public CityDto toDto(City city) {
        return new CityDto(
                city.getName(),
                city.getArea(),
                city.getPopulation()
        );
    }

    public City fromDto(CityDto cityDto) {
        return new City(
                cityDto.getName(),
                cityDto.getArea(),
                cityDto.getPopulation()
        );
    }
}
