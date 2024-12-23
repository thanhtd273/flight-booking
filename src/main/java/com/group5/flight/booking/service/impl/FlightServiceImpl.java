package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.AppUtils;
import com.group5.flight.booking.core.DataStatus;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.FlightDao;
import com.group5.flight.booking.dto.*;
import com.group5.flight.booking.model.Airport;
import com.group5.flight.booking.model.Flight;
import com.group5.flight.booking.model.Seat;
import com.group5.flight.booking.service.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightDao flightDao;

    private final PlaneService planeService;

    private final AirportService airportService;

    private final AirlineService airlineService;

    private final SeatService seatService;

    private final EntityManager entityManager;

    private static final String DEPARTURE_DATE = "departureDate";

    private static final String RETURN_DATE = "returnDate";

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
    public List<FlightInfo> filter(FilterCriteria filterCriteria)
            throws LogicException {
        Long fromAirportId = filterCriteria.getFromAirportId();
        Long toAirportId = filterCriteria.getToAirportId();
        Date departureDate = filterCriteria.getDepartureDate();

        if (ObjectUtils.isEmpty(filterCriteria.getFromAirportId())
                || ObjectUtils.isEmpty(filterCriteria.getToAirportId())
                || ObjectUtils.isEmpty(filterCriteria.getDepartureDate())) {
            throw new LogicException(ErrorCode.BLANK_FIELD);
        }
        if (ObjectUtils.isEmpty(filterCriteria)) {
            return findFlight(fromAirportId, toAirportId, departureDate);
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> criteriaQuery = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> root = criteriaQuery.from(Flight.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(root.get("fromAirportId"), fromAirportId));
        predicates.add(criteriaBuilder.equal(root.get("toAirportId"), toAirportId));

        Date startOfDay = setHourAndMinuteAndSecond(departureDate, LocalTime.of(0, 0, 0));
        Date endOfDay = setHourAndMinute(departureDate, LocalTime.of(23, 59, 59));

        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(DEPARTURE_DATE), startOfDay));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(DEPARTURE_DATE), endOfDay));
        
        Float minPrice = filterCriteria.getMinPrice();
        if (!ObjectUtils.isEmpty(minPrice))
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("basePrice"), minPrice));


        Float maxPrice = filterCriteria.getMaxPrice();
        if (!ObjectUtils.isEmpty(maxPrice))
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("basePrice"), maxPrice));

        List<Range<LocalTime>> departureTimes = filterCriteria.getDepartureTimes();
        if (!ObjectUtils.isEmpty(departureTimes)) {
            departureTimes.forEach(range -> {
                        if (!ObjectUtils.isEmpty(range.getMinimum())) {
                            Date startDate = setHourAndMinute(departureDate, range.getMinimum());
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(DEPARTURE_DATE), startDate));
                        }
                        if (!ObjectUtils.isEmpty(range.getMaximum())) {
                            Date startDate = setHourAndMinute(departureDate, range.getMaximum());
                            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(DEPARTURE_DATE), startDate));
                        }
                    }
            );
        }

        List<Range<LocalTime>> arrivalTimes = filterCriteria.getArrivalTimes();
        if (!ObjectUtils.isEmpty(arrivalTimes)) {
            arrivalTimes.forEach(range -> {
                        if (!ObjectUtils.isEmpty(range.getMinimum())) {
                            Date startDate = setHourAndMinute(departureDate, range.getMinimum());
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(RETURN_DATE), startDate));
                        }
                        if (!ObjectUtils.isEmpty(range.getMaximum())) {
                            Date startDate = setHourAndMinute(departureDate, range.getMaximum());
                            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(RETURN_DATE), startDate));
                        }
                    }
            );
        }


        List<Long> airlineIds = filterCriteria.getAirlineIds();
        if (!ObjectUtils.isEmpty(airlineIds)) {
            predicates.add(root.get("airlineId").in(airlineIds));
        }

        criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));
        List<Flight> flights = entityManager.createQuery(criteriaQuery).getResultList();

        return flights.stream().map(this::getFlightInfo).toList();
    }

    @Override
    public FlightInfo getFlightInfo(Long flightId) {
        Flight flight = findByFlightId(flightId);
        if (ObjectUtils.isEmpty(flight)) return null;
        return getFlightInfo(flight);
    }

    @Override
    public List<FlightDisplayInfo> getFlightsDisplay(List<FlightInfo> flightInfos) {
        List<FlightDisplayInfo> flightDisplayInfos = new LinkedList<>();

        for (FlightInfo flightInfo: flightInfos) {

            flightDisplayInfos.add(new FlightDisplayInfo(flightInfo.getFlightId(), flightInfo.getFromAirport().getName(),
                    flightInfo.getToAirport().getName(), AppUtils.formatDate(flightInfo.getDepatureDate()),
                    AppUtils.formatDate(flightInfo.getReturnDate()), flightInfo.getBasePrice()));
        }
        return flightDisplayInfos;
    }

    @Override
    public List<SeatInfo> getFlightSeats(Long flightId) throws LogicException {
        Flight flight = findByFlightId(flightId);
        if (ObjectUtils.isEmpty(flight)) {
            throw new LogicException("Not found flight");
        }
        List<SeatInfo> seatInfoList = new LinkedList<>();
        List<Seat> seats = seatService.findByPlaneId(flight.getPlaneId());
        for (Seat seat: seats) {
            SeatInfo seatInfo = seatService.getSeatInfo(seat.getPlaneId(), flightId);
            seatInfoList.add(seatInfo);
        }
        return seatInfoList;
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

        return new FlightInfo(flight.getFlightId(), planeId, planeInfo, airlineId, airlineInfo, fromAirportId, fromAirport, toAirportId, toAirport,
                flight.getDepartureDate(), flight.getReturnDate(), flight.getBasePrice(), flight.getNumOfPassengers());
    }

    private Date setHourAndMinute(Date date, LocalTime time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
        calendar.set(Calendar.MINUTE, time.getMinute());
        return calendar.getTime();
    }

    private Date setHourAndMinuteAndSecond(Date date, LocalTime time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
        calendar.set(Calendar.MINUTE, time.getMinute());
        calendar.set(Calendar.SECOND, time.getSecond());
        return calendar.getTime();
    }
}
