package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NationDao extends JpaRepository<Nation, Long> {
    List<Nation> findByDeletedFalse();
    Nation findByNationIdAndDeletedFalse(Long nationId);
}
