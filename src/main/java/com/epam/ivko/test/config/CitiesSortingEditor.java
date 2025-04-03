package com.epam.ivko.test.config;

import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class CitiesSortingEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        if (text == null) {
            setValue(null);
            return;
        }

        String uppercaseText = text.toUpperCase();
        setValue(uppercaseText);
    }
}