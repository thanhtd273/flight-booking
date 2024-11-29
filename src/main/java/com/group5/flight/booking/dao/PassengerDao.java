package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerDao extends JpaRepository<Passenger, Long> {

    List<Passenger> findAll();

    Optional<Passenger> findById(Long passengerId);

    List<Passenger> findByLastName(String lastName);

    void deleteById(Long passengerId);

}
