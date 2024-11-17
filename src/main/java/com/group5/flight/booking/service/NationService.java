package com.group5.flight.booking.service;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.NationInfo;
import com.group5.flight.booking.model.Nation;

import java.util.List;

public interface NationService {

    List<Nation> getAllNations();

    Nation findByNationId(Long nationId);
    Nation create(NationInfo nationInfo) throws LogicException;

    Nation update(Long nationId, NationInfo nationInfo) throws LogicException;

    ErrorCode delete(Long nationId);

    NationInfo getNationInfo(Long nationId);

}
