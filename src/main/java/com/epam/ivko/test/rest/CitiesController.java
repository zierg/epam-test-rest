package com.epam.ivko.test.rest;

import com.epam.ivko.test.service.CitiesService;
import com.epam.ivko.test.service.GetCitiesParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<CityResponseEntity> getCities(
            @RequestParam(name = "add-density", required = false, defaultValue = "false") boolean enhanceWithDensity
    ) {
        var paramsBuilder = GetCitiesParams.builder();

        paramsBuilder.enhanceWithDensity(enhanceWithDensity);

        GetCitiesParams params = paramsBuilder.build();

        return citiesService.getCities(params);
    }
}
