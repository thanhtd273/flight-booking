package com.group1.flight.booking.dao;

import com.group1.flight.booking.model.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneDao extends JpaRepository<Plane, Long> {
    List<Plane> findByDeletedFalse();

    Plane findByPlaneIdAndDeletedFalse(Long planeId);
}
