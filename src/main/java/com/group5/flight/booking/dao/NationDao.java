package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationDao extends JpaRepository<Nation, Long> {
    Nation findByNationIdAndDeletedFalse(Long nationId);
}
