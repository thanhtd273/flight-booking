package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDao extends JpaRepository<City, Long> {
    City findByCityIdAndDeletedFalse(Long cityId);
}
