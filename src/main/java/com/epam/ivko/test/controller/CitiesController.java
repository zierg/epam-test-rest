package com.epam.ivko.test.controller;

import com.epam.ivko.test.dto.CityDto;
import com.epam.ivko.test.dto.JsonViews;
import com.epam.ivko.test.service.CitiesService;
import com.epam.ivko.test.service.CitiesSorting;
import com.epam.ivko.test.service.GetCitiesParams;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
public class CitiesController {

    // todo: tests (unit and controller)

    private final CitiesService citiesService;

    public CitiesController(
            CitiesService citiesService
    ) {
        this.citiesService = citiesService;
    }

    @GetMapping("/cities")
    @JsonView(JsonViews.ResponseView.class)
    @Operation(
            summary = "Get list of cities",
            description = "Returns a list of cities with some additional changes if specified"
    )
    public List<CityDto> getCities(
            @RequestParam(name = "add-density", required = false, defaultValue = "false") boolean enhanceWithDensity,
            @RequestParam(name = "sort-by", required = false, defaultValue = "none") CitiesSorting citiesSorting,
            @RequestParam(name = "sort-descending", required = false, defaultValue = "false") boolean sortDescending,
            @RequestParam(name = "name-contains", required = false) String nameContains
    ) {
        GetCitiesParams params = GetCitiesParams.builder()
                .enhanceWithDensity(enhanceWithDensity)
                .sorting(citiesSorting)
                .sortDescending(sortDescending)
                .nameContains(nameContains)
                .build();

        return citiesService.getCities(params);
    }

    @PutMapping("/cities")
    @JsonView(JsonViews.RequestView.class)
    @Operation(summary = "Add or update a city")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "City successfully created/updated"
            )
    })
    public void addCity(@RequestBody CityDto city) {
        citiesService.addCity(city);
    }
}
