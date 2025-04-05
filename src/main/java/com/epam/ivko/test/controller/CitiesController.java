package com.epam.ivko.test.controller;

import com.epam.ivko.test.dto.CityDto;
import com.epam.ivko.test.dto.JsonViews;
import com.epam.ivko.test.service.CitiesService;
import com.epam.ivko.test.service.CitiesSorting;
import com.epam.ivko.test.service.GetCitiesParams;
import com.epam.ivko.test.storage.IncorrectCityException;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
public class CitiesController { // todo: swagger

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

    @PostMapping("/cities")
    @JsonView(JsonViews.RequestView.class)
    @Operation(summary = "Add a city")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "City successfully created"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "City with that name already exists"
            )
    })
    public void addCity(@RequestBody CityDto city) {
        citiesService.addCity(city);
    }

    @ExceptionHandler(IncorrectCityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleException(IncorrectCityException ex) {
        return ex.getMessage();
    }
}
