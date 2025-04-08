package com.epam.ivko.task.storage;

import com.epam.ivko.task.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, String>, JpaSpecificationExecutor<City> {
    // JpaSpecificationExecutor is needed for dynamic queries
}