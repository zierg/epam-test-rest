package com.epam.ivko.test.config;

import com.epam.ivko.test.service.CitiesSorting;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@SuppressWarnings("unused")
@ControllerAdvice
public class WebConfig {

    private final CitiesSortingEditor citiesSortingEditor;

    public WebConfig(
            CitiesSortingEditor citiesSortingEditor
    ) {
        this.citiesSortingEditor = citiesSortingEditor;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(CitiesSorting.class, citiesSortingEditor);
    }
}
