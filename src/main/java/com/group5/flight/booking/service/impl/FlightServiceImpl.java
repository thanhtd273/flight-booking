package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.DataStatus;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.FlightDao;
import com.group5.flight.booking.dto.*;
import com.group5.flight.booking.model.Airport;
import com.group5.flight.booking.model.Flight;
import com.group5.flight.booking.service.AirlineService;
import com.group5.flight.booking.service.AirportService;
import com.group5.flight.booking.service.FlightService;
import com.group5.flight.booking.service.PlaneService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightDao flightDao;

    private final PlaneService planeService;

    private final AirportService airportService;

    private final AirlineService airlineService;

    private final EntityManager entityManager;

    @Override
    public Flight create(FlightInfo flightInfo) throws LogicException {
        if (ObjectUtils.isEmpty(flightInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Flight info is empty");
        }
        if (flightInfo.isAnyNull()) {
            throw new LogicException(ErrorCode.BLANK_FIELD);
        }
        Airport departureAirport = airportService.findByAirportId(flightInfo.getFromAirportId());
        if (ObjectUtils.isEmpty(departureAirport)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Departure airport does not exist");
        }
        Airport destinationAirport = airportService.findByAirportId(flightInfo.getToAirportId());
        if (ObjectUtils.isEmpty(destinationAirport)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Destination airport does not exist");
        }

        Flight flight = new Flight();
        flight.setAirlineId(flightInfo.getAirlineId());
        flight.setPlaneId(flightInfo.getPlaneId());
        flight.setFromAirportId(flightInfo.getFromAirportId());
        flight.setToAirportId(flightInfo.getToAirportId());
        flight.setDepartureDate(flightInfo.getDepatureDate());
        if (!ObjectUtils.isEmpty(flightInfo.getReturnDate())) {
            flight.setReturnDate(flightInfo.getReturnDate());
        }
        flight.setBasePrice(flightInfo.getBasePrice());
        flight.setNumOfPassengers(0);
        flight.setCreatedAt(new Date(System.currentTimeMillis()));
        flight.setStatus(DataStatus.ACTIVE);

        return flightDao.save(flight);
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightDao.findAll();
    }

    @Override
    public Flight findByFlightId(Long flightId) {
        return flightDao.findByFlightId(flightId);
    }

    @Override
    public List<FlightInfo> findFlight(Long fromAirportId, Long toAirportId, Date departureDate) throws LogicException {
        List<Flight> flights = flightDao.findByFromAndToAirportAndDepartureDate(fromAirportId, toAirportId, departureDate);

        return flights.stream().map(this::getFlightInfo).toList();
    }

    @Override
    public List<Flight> search(SearchCriteria searchCriteria) throws LogicException {
        String hql = "SELECT u FROM Flight u ORDER BY createdAt";
        Query<Flight> query = (Query<Flight>) entityManager.createQuery(hql);

        return query.getResultList();
    }

    @Override
    public FlightInfo getFlightInfo(Long flightId) {
        Flight flight = findByFlightId(flightId);
        if (ObjectUtils.isEmpty(flight)) return null;
        return getFlightInfo(flight);
    }

    private FlightInfo getFlightInfo(Flight flight) {
        if (ObjectUtils.isEmpty(flight)) return null;

        Long planeId = flight.getPlaneId();
        PlaneInfo planeInfo = planeService.getPlaneInfo(planeId);
        Long airlineId = flight.getAirlineId();
        AirlineInfo airlineInfo = airlineService.getAirlineInfo(airlineId);
        Long fromAirportId = flight.getFromAirportId();
        AirportInfo fromAirport = airportService.getAirportInfo(fromAirportId);
        Long toAirportId = flight.getToAirportId();
        AirportInfo toAirport = airportService.getAirportInfo(toAirportId);

        return new FlightInfo(planeId, planeInfo, airlineId, airlineInfo, fromAirportId, fromAirport, toAirportId, toAirport,
                flight.getDepartureDate(), flight.getReturnDate(), flight.getBasePrice(), flight.getNumOfPassengers());
    }
}
