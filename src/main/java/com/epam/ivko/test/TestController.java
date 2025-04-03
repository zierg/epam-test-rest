package com.epam.ivko.test;

import com.epam.ivko.test.entity.City;
import com.epam.ivko.test.storage.CitiesStorage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@SuppressWarnings("unused")
@RestController
public class TestController {

    private final CitiesStorage storage;

    public TestController(
            CitiesStorage storage
    ) {
        this.storage = storage;
    }

    @GetMapping("/test")
    Set<City> getCities() {
        return storage.getAll();
    }
}
