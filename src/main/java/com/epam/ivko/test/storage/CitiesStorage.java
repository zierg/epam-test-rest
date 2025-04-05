package com.epam.ivko.test.storage;

import com.epam.ivko.test.entity.City;
import com.epam.ivko.test.service.CitiesSorting;
import com.epam.ivko.test.service.GetCitiesParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CitiesStorage {

    private final JsonPersistentStorage jsonStorage;
    private final CityRepository cityRepository;
    private final boolean persistChanges;

    public CitiesStorage(
            JsonPersistentStorage jsonStorage,
            CityRepository cityRepository,
            @Value("${cities.persist-changes:#{false}}") boolean persistChanges
    ) {
        this.jsonStorage = jsonStorage;
        this.cityRepository = cityRepository;
        this.persistChanges = persistChanges;

        loadCities();
    }

    public void addCity(City city) {
        cityRepository.save(city);

        if (persistChanges) {
            jsonStorage.addCity(city);
        }
    }

    public Collection<City> getCities(GetCitiesParams params) {
        Optional<Specification<City>> nameContainsSpec = getNameContainsSpec(params);
        Sort sort = getSorting(params);

        //noinspection OptionalIsPresent
        if (nameContainsSpec.isPresent()) {
            return cityRepository.findAll(nameContainsSpec.get(), sort);
        } else {
            return cityRepository.findAll(sort);
        }
    }

    private void loadCities() {
        Collection<City> cities = jsonStorage.getCities();
        cityRepository.saveAll(cities);
    }

    private Optional<Specification<City>> getNameContainsSpec(GetCitiesParams params) {
        String nameContains = params.getNameContains();

        if (nameContains == null || nameContains.isEmpty()) {
            return Optional.empty();
        }

        Specification<City> spec = (root, _, builder) -> builder.like(
                builder.lower(root.get("name")),
                "%" + nameContains + "%"
        );

        return Optional.of(spec);
    }

    private Sort getSorting(GetCitiesParams params) {
        CitiesSorting sorting = params.getSorting();

        if (sorting == null || sorting == CitiesSorting.NONE) {
            return Sort.unsorted();
        }

        Sort.Direction direction = params.isSortDescending() ? Sort.Direction.DESC : Sort.Direction.ASC;

        return Sort.by(direction, sorting.getFieldName());
    }
}
