package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.PassengerDao;
import com.group5.flight.booking.dto.PassengerInfo;
import com.group5.flight.booking.model.Passenger;
import com.group5.flight.booking.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private static final Logger logger = LoggerFactory.getLogger(PassengerServiceImpl.class);
    private final PassengerDao passengerDao;

    @Override
    public List<Passenger> getAllPassengers() {
        logger.info("Fetching all passengers");
        return passengerDao.findAll();
    }

    @Override
    public Passenger findByPassengerId(Long id) throws LogicException {
        logger.info("Finding passenger by ID: {}", id);
        return passengerDao.findById(id)
            .orElseThrow(() -> new LogicException(ErrorCode.PASSENGER_NOT_FOUND, 
                String.format("Passenger with id %d not found", id)));
    }

    @Override
    public Passenger create(PassengerInfo passengerInfo) throws LogicException {
        logger.info("Creating passenger with info: {}", passengerInfo);
        if (ObjectUtils.isEmpty(passengerInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Passenger info is empty");
        }
        validatePassengerInfo(passengerInfo);

        Passenger passenger = new Passenger();
        passenger.setFirstName(passengerInfo.getFirstName());
        passenger.setLastName(passengerInfo.getLastName());
        passenger.setEmail(passengerInfo.getEmail());
        passenger.setPhone(passengerInfo.getPhone());
        passenger.setBirthday(passengerInfo.getBirthday());
        passenger.setGender(passengerInfo.getGender());
        passenger.setCreatedAt(new java.util.Date());
        passenger.setUpdatedAt(new java.util.Date());

        return passengerDao.save(passenger);
    }

    @Override
    public ErrorCode delete(Long id) throws LogicException {
        logger.info("Deleting passenger with ID: {}", id);
        Passenger passenger = findByPassengerId(id);
        passengerDao.deleteById(id);
        return ErrorCode.SUCCESS;
    }

    @Override
    public Passenger update(Long id, PassengerInfo passengerInfo) throws LogicException {
        logger.info("Updating passenger with ID: {}", id);
        Passenger passenger = findByPassengerId(id);
        if (!ObjectUtils.isEmpty(passengerInfo.getFirstName())) {
            passenger.setFirstName(passengerInfo.getFirstName());
        }
        if (!ObjectUtils.isEmpty(passengerInfo.getLastName())) {
            passenger.setLastName(passengerInfo.getLastName());
        }
        if (!ObjectUtils.isEmpty(passengerInfo.getEmail())) {
            passenger.setEmail(passengerInfo.getEmail());
        }
        if (!ObjectUtils.isEmpty(passengerInfo.getPhone())) {
            passenger.setPhone(passengerInfo.getPhone());
        }
        if (!ObjectUtils.isEmpty(passengerInfo.getBirthday())) {
            passenger.setBirthday(passengerInfo.getBirthday());
        }
        if (!ObjectUtils.isEmpty(passengerInfo.getGender())) {
            passenger.setGender(passengerInfo.getGender());
        }
        passenger.setUpdatedAt(new java.util.Date());

        return passengerDao.save(passenger);
    }

    @Override
    public List<Passenger> findByLastName(String lastName) {
        logger.info("Finding passengers by last name: {}", lastName);
        return passengerDao.findByLastName(lastName);
    }

    @Override
    public PassengerInfo getPassengerInfo(Long id) throws LogicException {
        logger.info("Getting passenger info for ID: {}", id);
        Passenger passenger = findByPassengerId(id);
        return new PassengerInfo(
            passenger.getFirstName(),
            passenger.getLastName(),
            passenger.getBirthday(),
            passenger.getGender(),
            passenger.getEmail(),
            passenger.getPhone()
        );
    }

    private void validatePassengerInfo(PassengerInfo info) throws LogicException {
        if (!StringUtils.hasText(info.getFirstName())) {
            throw new LogicException(ErrorCode.INVALID_PASSENGER_INFO, "First name is required");
        }
        if (!StringUtils.hasText(info.getLastName())) {
            throw new LogicException(ErrorCode.INVALID_PASSENGER_INFO, "Last name is required");
        }
        if (!StringUtils.hasText(info.getEmail())) {
            throw new LogicException(ErrorCode.INVALID_PASSENGER_INFO, "Email is required");
        }
        if (!StringUtils.hasText(info.getPhone())) {
            throw new LogicException(ErrorCode.INVALID_PASSENGER_INFO, "Phone number is required");
        }
    }
}
