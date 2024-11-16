package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneDao extends JpaRepository<Plane, Long> {
}
