package com.epam.ivko.test.config;

import com.epam.ivko.test.service.CitiesSorting;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@SuppressWarnings("unused")
@ControllerAdvice
public class WebConfig {

    private final StringToEnumIgnoreCaseConverter stringToEnumIgnoreCaseConverter;

    public WebConfig(StringToEnumIgnoreCaseConverter stringToEnumIgnoreCaseConverter) {
        this.stringToEnumIgnoreCaseConverter = stringToEnumIgnoreCaseConverter;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(CitiesSorting.class, stringToEnumIgnoreCaseConverter);
    }
}
