package test.com.epam.ivko.task.controller;

import com.epam.ivko.task.TestApplication;
import com.epam.ivko.task.dto.CityDto;
import com.epam.ivko.task.service.CitiesService;
import com.epam.ivko.task.service.CitiesSorting;
import com.epam.ivko.task.service.GetCitiesParams;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unused")
@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc
public class CitiesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CitiesService citiesService;

    @Test
    public void testAddCity() throws Exception {
        CityDto cityDto = new CityDto();
        cityDto.setArea(30.30);
        cityDto.setPopulation(30);

        String cityName = "testCity";

        mockMvc.perform(
                put("/cities/{cityName}", cityName)
                        .content(objectMapper.writeValueAsString(cityDto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        CityDto expectedDto = new CityDto(
                cityName,
                cityDto.getArea(),
                cityDto.getPopulation()
        );

        verify(citiesService).addCity(expectedDto);
    }

    @Test
    public void testGetCitiesEndpointWithoutParams() throws Exception {
        List<CityDto> expectedResponse = List.of(new CityDto(
                "testCity",
                10.10,
                10
        ));

        GetCitiesParams defaultParams = GetCitiesParams.builder().build();

        when(citiesService.getCities(defaultParams))
                .thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(
                        get("/cities")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        verify(citiesService).getCities(defaultParams);
        List<CityDto> actualResponse = convertResponseToDto(result);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetCitiesWithAllParams() throws Exception {
        List<CityDto> expectedResponse = List.of(new CityDto(
                "testParamsCity",
                20.20,
                20
        ));

        String nameContains = "test-substring";

        GetCitiesParams params = GetCitiesParams.builder()
                .enhanceWithDensity(true)
                .sorting(CitiesSorting.NAME)
                .sortDescending(true)
                .nameContains(nameContains)
                .build();

        when(citiesService.getCities(params))
                .thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(
                        get("/cities")
                                .param("add-density", "true")
                                .param("sort-by", "name")
                                .param("sort-descending", "true")
                                .param("name-contains", nameContains)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        verify(citiesService).getCities(params);
        List<CityDto> actualResponse = convertResponseToDto(result);

        assertEquals(expectedResponse, actualResponse);
    }

    @ParameterizedTest
    @EnumSource(CitiesSorting.class)
    public void testSortingFieldsLowercase(CitiesSorting sorting) throws Exception {
        // verify that all Enum values are correctly work with parameters passed in lower case

        String sortBy = sorting.name().toLowerCase();

        mockMvc.perform(
                get("/cities")
                        .param("sort-by", sortBy)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        GetCitiesParams expectedParams = GetCitiesParams.builder()
                .sorting(sorting)
                .build();

        verify(citiesService).getCities(expectedParams);
    }

    private List<CityDto> convertResponseToDto(MvcResult result) throws Exception {
        return objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
    }
}
