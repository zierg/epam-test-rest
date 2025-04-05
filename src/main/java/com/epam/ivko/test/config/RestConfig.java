package com.epam.ivko.test.config;

import com.epam.ivko.test.service.CitiesSorting;
import com.epam.ivko.test.storage.IncorrectCityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ExceptionHandler(IncorrectCityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleExistingCityException(IncorrectCityException ex) {
        return ex.getMessage();
    }
}
