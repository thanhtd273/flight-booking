package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.AirlineDao;
import com.group5.flight.booking.dto.AirlineInfo;
import com.group5.flight.booking.model.Airline;
import com.group5.flight.booking.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineDao airlineDao;

    @Override
    public Airline findByAirlineId(Long airlineId) {
        return airlineDao.findByAirlineIdAndDeletedFalse(airlineId);
    }

    @Override
    public Airline create(AirlineInfo airlineInfo) throws LogicException {
        if (ObjectUtils.isEmpty(airlineInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL);
        }
        String code = airlineInfo.getCode();
        if (ObjectUtils.isEmpty(airlineInfo.getName()) || ObjectUtils.isEmpty(code)) {
            throw new LogicException(ErrorCode.BLANK_FIELD, "Airline's name and code are required");
        }

        Airline airline = new Airline();
        airline.setCode(code);
        airline.setName(airlineInfo.getName());
        airline.setCreatedAt(new Date(System.currentTimeMillis()));
        airline.setDeleted(false);

        return airlineDao.save(airline);
    }

    @Override
    public Airline update(Long airlineId, AirlineInfo airlineInfo) throws LogicException {
        if (ObjectUtils.isEmpty(airlineInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Airline info is null");
        }
        Airline airline = findByAirlineId(airlineId);
        if (ObjectUtils.isEmpty(airline)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Airline does not exist");
        }
        if (!ObjectUtils.isEmpty(airlineInfo.getName())) {
            airline.setName(airlineInfo.getName());
        }
        if (!ObjectUtils.isEmpty(airlineInfo.getCode())) {
            airline.setCode(airlineInfo.getCode());
        }
        airline.setUpdatedAt(new Date(System.currentTimeMillis()));
        return airlineDao.save(airline);
    }

    @Override
    public ErrorCode delete(Long airlineId) {
        Airline airline = findByAirlineId(airlineId);
        if (ObjectUtils.isEmpty(airline))
            return ErrorCode.DATA_NULL;
        airline.setDeleted(true);
        airline.setUpdatedAt(new Date(System.currentTimeMillis()));
        return ErrorCode.SUCCESS;
    }
}
