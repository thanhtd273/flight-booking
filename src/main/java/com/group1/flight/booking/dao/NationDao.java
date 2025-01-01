package com.group1.flight.booking.dao;

import com.group1.flight.booking.model.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NationDao extends JpaRepository<Nation, Long> {

    @Query("SELECT u FROM Nation u ORDER BY u.name ASC")
    List<Nation> findAllNations();

    Nation findByNationIdAndDeletedFalse(Long nationId);
}
