package com.epam.ivko.test.rest;

import com.epam.ivko.test.service.CitiesService;
import com.epam.ivko.test.service.CitiesSorting;
import com.epam.ivko.test.service.GetCitiesParams;
import com.epam.ivko.test.storage.IncorrectCityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
public class CitiesController {

    private final CitiesService citiesService;

    public CitiesController(
            CitiesService citiesService
    ) {
        this.citiesService = citiesService;
    }

    @GetMapping("/cities")
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
    public void addCity(@RequestBody CityDto city) {
        citiesService.addCity(city);
    }

    @ExceptionHandler(IncorrectCityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(IncorrectCityException ex) {
        return ex.getMessage();
    }
}
