package com.group1.flight.booking.service;

import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.dto.PlaneInfo;
import com.group1.flight.booking.model.Plane;

import java.util.List;

public interface PlaneService {
    List<Plane> getAllPlanes();

    Plane findByPlaneId(Long planeId);

    Plane create(PlaneInfo planeInfo) throws LogicException;

    Plane update(Long planeId, PlaneInfo planeInfo) throws LogicException;

    ErrorCode delete(Long planeId);

    PlaneInfo getPlaneInfo(Long planeId);
}
