package com.group5.flight.booking.service;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.CityInfo;
import com.group5.flight.booking.model.City;

public interface CityService {

    City findByCityId(Long cityId);
    City create(CityInfo cityInfo) throws LogicException;

    City update(Long cityId, CityInfo cityInfo) throws LogicException;

    ErrorCode delete(Long cityId);
}
