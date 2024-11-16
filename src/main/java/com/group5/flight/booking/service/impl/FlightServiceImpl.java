package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.SearchCriteria;
import com.group5.flight.booking.model.Flight;
import com.group5.flight.booking.service.FlightService;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Flight> search(SearchCriteria searchCriteria) throws LogicException {
        String hql = "SELECT u FROM Flight u ORDER BY createdAt";
        Query<Flight> query = (Query<Flight>) entityManager.createQuery(hql);

        return query.getResultList();
    }
}
