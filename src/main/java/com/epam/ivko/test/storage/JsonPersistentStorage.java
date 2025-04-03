package com.epam.ivko.test.storage;

import com.epam.ivko.test.storage.entity.City;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@Component
public class JsonPersistentStorage {

    private final String filePath;

    public JsonPersistentStorage(
            @Value("${cities.source-file}") String filePath
    ) {
        this.filePath = filePath;
    }

    public Set<City> getCities() {
        return loadFileFromResources(filePath);
    }

    private Set<City> loadFileFromResources(String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            File file = ResourceUtils.getFile("classpath:" + filePath);
            return mapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
