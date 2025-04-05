package com.epam.ivko.test.mapping;

import com.epam.ivko.test.dto.CityDto;
import com.epam.ivko.test.entity.City;
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
