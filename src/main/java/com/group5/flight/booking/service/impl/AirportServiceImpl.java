package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.AirportDao;
import com.group5.flight.booking.dto.AirportInfo;
import com.group5.flight.booking.dto.CityInfo;
import com.group5.flight.booking.model.Airport;
import com.group5.flight.booking.model.City;
import com.group5.flight.booking.service.AirportService;
import com.group5.flight.booking.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportDao airportDao;

    private final CityService cityService;

    @Override
    public List<Airport> getAllAirports() {
        return airportDao.findByDeletedFalse();
    }

    @Override
    public Airport findByAirportId(Long airportId) {
        return airportDao.findByAirportIdAndDeletedFalse(airportId);
    }

    @Override
    public Airport create(AirportInfo airportInfo) throws LogicException {
        if (ObjectUtils.isEmpty(airportInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL);
        }
        if (!airportInfo.isAllNotNull()) {
            throw new LogicException(ErrorCode.BLANK_FIELD);
        }
        City city = cityService.findByCityId(airportInfo.getCityId());
        if (ObjectUtils.isEmpty(city)) {
            throw new LogicException(ErrorCode.DATA_NULL, "City does not exist");
        }

        Airport airport = new Airport();
        airport.setName(airportInfo.getName());
        airport.setAirportCode(airportInfo.getAirportCode());
        airport.setCityId(city.getCityId());
        airport.setCreatedAt(new Date(System.currentTimeMillis()));
        airport.setDeleted(false);
        return airportDao.save(airport);
    }

    @Override
    public Airport update(Long airportId, AirportInfo airportInfo) throws LogicException {
        if (ObjectUtils.isEmpty(airportInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL);
        }
        Airport airport = findByAirportId(airportId);
        if (ObjectUtils.isEmpty(airport)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Airport does not exist");
        }
        if (!ObjectUtils.isEmpty(airportInfo.getName())) {
            airport.setName(airportInfo.getName());
        }
        if (!ObjectUtils.isEmpty(airportInfo.getAirportCode())) {
            airport.setAirportCode(airportInfo.getAirportCode());
        }
        if (!ObjectUtils.isEmpty(airportInfo.getCityId())) {
            City city = cityService.findByCityId(airportInfo.getCityId());
            if (ObjectUtils.isEmpty(city))
                throw new LogicException(ErrorCode.DATA_NULL, "City does not exist");
            airport.setCityId(city.getCityId());
        }
        airport.setUpdatedAt(new Date(System.currentTimeMillis()));

        return airportDao.save(airport);
    }

    @Override
    public ErrorCode delete(Long airportId) {
        Airport airport = findByAirportId(airportId);
        if (ObjectUtils.isEmpty(airport))
            return ErrorCode.DATA_NULL;
        airport.setDeleted(true);
        airport.setUpdatedAt(new Date(System.currentTimeMillis()));
        airportDao.save(airport);
        return ErrorCode.SUCCESS;
    }

    @Override
    public AirportInfo getAirportInfo(Long airportId) {
        Airport airport = findByAirportId(airportId);
        if (ObjectUtils.isEmpty(airport)) return null;

        CityInfo cityInfo = cityService.getCityInfo(airport.getCityId());
        return new AirportInfo(airport.getName(), airport.getAirportCode(), airport.getCityId(), cityInfo);
    }
}
