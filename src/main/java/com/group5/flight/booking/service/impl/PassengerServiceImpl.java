package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.PassengerDao;
import com.group5.flight.booking.dto.NationInfo;
import com.group5.flight.booking.dto.PassengerInfo;
import com.group5.flight.booking.model.Nation;
import com.group5.flight.booking.model.Passenger;
import com.group5.flight.booking.service.NationService;
import com.group5.flight.booking.service.PassengerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerDao passengerDao;

    private final NationService nationService;

    @Override
    public List<Passenger> getAllPassengers() {
        return passengerDao.findByDeletedFalse();
    }

    @Override
    public Passenger findByPassengerId(Long passengerId) {
        return passengerDao.findByPassengerIdAndDeletedFalse(passengerId);
    }

    @Override
    public Passenger create(PassengerInfo passengerInfo) throws LogicException {
        if (ObjectUtils.isEmpty(passengerInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL);
        }
        if (ObjectUtils.isEmpty(passengerInfo.getFirstName()) || ObjectUtils.isEmpty(passengerInfo.getLastName())
                || ObjectUtils.isEmpty(passengerInfo.getBirthday()) || ObjectUtils.isEmpty(passengerInfo.getNationalityId())) {
            throw new LogicException(ErrorCode.BLANK_FIELD, "Passenger's info is required");
        }

        Nation nation = nationService.findByNationId(passengerInfo.getNationalityId());
        if (ObjectUtils.isEmpty(nation)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Nation does not found");
        }

        Passenger passenger = new Passenger();
        passenger.setNationalityId(passengerInfo.getNationalityId());
        passenger.setFirstName(passengerInfo.getFirstName());
        passenger.setLastName(passengerInfo.getLastName());
        passenger.setBirthday(passengerInfo.getBirthday());
        if (!ObjectUtils.isEmpty(passengerInfo.getGender()))
            passenger.setGender(passengerInfo.getGender());

        if (!ObjectUtils.isEmpty(passengerInfo.getPhone()))
            passenger.setPhone(passengerInfo.getPhone());
        if (!ObjectUtils.isEmpty(passengerInfo.getEmail()))
            passenger.setEmail(passengerInfo.getEmail());

        passenger.setCreatedAt(new Date(System.currentTimeMillis()));
        passenger.setDeleted(false);

        return passengerDao.save(passenger);
    }

    @Override
    @Transactional
    public List<Passenger> create(PassengerInfo[] passengerInfos) throws LogicException {
        List<Passenger> passengers = new LinkedList<>();
        for (PassengerInfo passengerInfo: passengerInfos) {
            passengers.add(create(passengerInfo));
        }

        return passengers;
    }

    @Override
    public Passenger update(Long passengerId, PassengerInfo passengerInfo) throws LogicException {
        if (ObjectUtils.isEmpty(passengerInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Passenger info is null");
        }
        Passenger passenger = findByPassengerId(passengerId);
        if (ObjectUtils.isEmpty(passenger)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Passenger does not exist");
        }
        if (!ObjectUtils.isEmpty(passengerInfo.getFirstName())) {
            passenger.setFirstName(passengerInfo.getFirstName());
        }
        if (!ObjectUtils.isEmpty(passengerInfo.getLastName())) {
            passenger.setLastName(passengerInfo.getLastName());
        }
        if (!ObjectUtils.isEmpty(passengerInfo.getBirthday())) {
            passenger.setBirthday(passengerInfo.getBirthday());
        }
        if (!ObjectUtils.isEmpty(passengerInfo.getGender())) {
            passenger.setGender(passengerInfo.getGender());
        }
        if (!ObjectUtils.isEmpty(passengerInfo.getPhone())) {
            passenger.setPhone(passengerInfo.getPhone());
        }
        if (!ObjectUtils.isEmpty(passengerInfo.getEmail())) {
            passenger.setEmail(passengerInfo.getEmail());
        }

        passenger.setUpdatedAt(new Date(System.currentTimeMillis()));
        return passengerDao.save(passenger);
    }

    @Override
    public ErrorCode delete(Long passengerId) {
        Passenger passenger = findByPassengerId(passengerId);
        if (ObjectUtils.isEmpty(passenger))
            return ErrorCode.DATA_NULL;
        passenger.setDeleted(true);
        passenger.setUpdatedAt(new Date(System.currentTimeMillis()));
        return ErrorCode.SUCCESS;
    }

    @Override
    public PassengerInfo getPassengerInfo(Long passengerId) {
        Passenger passenger = findByPassengerId(passengerId);
        if (ObjectUtils.isEmpty(passenger)) return null;

        PassengerInfo passengerInfo = new PassengerInfo();
        passengerInfo.setFirstName(passenger.getFirstName());
        passengerInfo.setLastName(passenger.getLastName());
        passengerInfo.setBirthday(passenger.getBirthday());
        passengerInfo.setEmail(passenger.getEmail());
        passengerInfo.setPhone(passenger.getPhone());

        NationInfo nationInfo = nationService.getNationInfo(passengerInfo.getNationalityId());
        passengerInfo.setNationalityId(passenger.getNationalityId());
        passengerInfo.setNationInfo(nationInfo);

        return passengerInfo;
    }
}
