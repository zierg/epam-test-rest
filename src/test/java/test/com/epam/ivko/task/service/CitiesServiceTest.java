package test.com.epam.ivko.task.service;


import com.epam.ivko.task.dto.CityDto;
import com.epam.ivko.task.entity.City;
import com.epam.ivko.task.mapping.CityMapper;
import com.epam.ivko.task.service.CitiesService;
import com.epam.ivko.task.service.DensityCalculator;
import com.epam.ivko.task.service.GetCitiesParams;
import com.epam.ivko.task.storage.CitiesStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CitiesServiceTest {

    @Mock
    private CitiesStorage storage;

    @Mock
    private CityMapper cityMapper;

    @Mock
    private DensityCalculator densityEnhancer;

    private final CityDto dto1 = new CityDto(
            "test1",
            1.1,
            1
    );
    private final City entity1 = new City(
            dto1.getName(),
            dto1.getArea(),
            dto1.getPopulation()
    );

    private final CityDto dto2 = new CityDto(
            "test2",
            2.2,
            2
    );
    private final City entity2 = new City(
            dto2.getName(),
            dto2.getArea(),
            dto2.getPopulation()
    );

    @Test
    public void testAddCity() {

        when(cityMapper.fromDto(dto1)).thenReturn(entity1);

        CitiesService service = new CitiesService(storage, cityMapper, densityEnhancer);
        service.addCity(dto1);

        verify(storage).addCity(entity1);
    }

    @Test
    public void testGetCities() {
        GetCitiesParams params = GetCitiesParams.builder().build();

        when(cityMapper.toDto(entity1)).thenReturn(dto1);
        when(cityMapper.toDto(entity2)).thenReturn(dto2);
        when(storage.getCities(params)).thenReturn(List.of(entity1, entity2));

        List<CityDto> expected = List.of(
                copy(dto1),
                copy(dto2)
        );

        CitiesService service = new CitiesService(storage, cityMapper, densityEnhancer);
        List<CityDto> actual = service.getCities(params);

        verify(storage).getCities(params);

        assertEquals(expected, actual);
    }

    @Test
    public void testDensity() {
        GetCitiesParams params = GetCitiesParams.builder()
                .enhanceWithDensity(true)
                .build();
        CityDto enhancedDto1 = copy(dto1);
        enhancedDto1.setDensity(10.10);

        CityDto enhancedDto2 = copy(dto2);
        enhancedDto2.setDensity(20.20);

        when(cityMapper.toDto(entity1)).thenReturn(dto1);
        when(cityMapper.toDto(entity2)).thenReturn(dto2);
        when(storage.getCities(params)).thenReturn(List.of(entity1, entity2));

        when(densityEnhancer.calculateDensity(dto1.getPopulation(), dto1.getArea()))
                .thenReturn(enhancedDto1.getDensity());
        when(densityEnhancer.calculateDensity(dto2.getPopulation(), dto2.getArea()))
                .thenReturn(enhancedDto2.getDensity());

        List<CityDto> expected = List.of(
                enhancedDto1,
                enhancedDto2
        );

        CitiesService service = new CitiesService(storage, cityMapper, densityEnhancer);
        List<CityDto> actual = service.getCities(params);

        verify(storage).getCities(params);

        assertEquals(expected, actual);
    }

    private CityDto copy(CityDto dto) {
        return new CityDto(
                dto.getName(),
                dto.getArea(),
                dto.getPopulation(),
                dto.getDensity()
        );
    }
}
