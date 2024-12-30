package com.group1.flight.booking.dao;

import com.group1.flight.booking.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityDao extends JpaRepository<City, Long> {

    List<City> findByDeletedFalse();
    City findByCityIdAndDeletedFalse(Long cityId);
}
