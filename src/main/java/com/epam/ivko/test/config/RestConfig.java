package com.epam.ivko.test.config;

import com.epam.ivko.test.service.CitiesSorting;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@SuppressWarnings("unused")
@RestControllerAdvice
public class RestConfig {

    private final StringToEnumIgnoreCaseConverter stringToEnumIgnoreCaseConverter;

    public RestConfig(StringToEnumIgnoreCaseConverter stringToEnumIgnoreCaseConverter) {
        this.stringToEnumIgnoreCaseConverter = stringToEnumIgnoreCaseConverter;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(CitiesSorting.class, stringToEnumIgnoreCaseConverter);
    }
}
