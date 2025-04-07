package com.epam.ivko.task.service;

import org.springframework.stereotype.Component;

@Component
public class DensityCalculator {

    public Double calculateDensity(Integer population, Double area) {
        return population / area;
    }
}
