package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.PlaneDao;
import com.group5.flight.booking.dto.PlaneInfo;
import com.group5.flight.booking.model.Plane;
import com.group5.flight.booking.service.PlaneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaneServiceImpl implements PlaneService {

    private final PlaneDao planeDao;

    @Override
    public List<Plane> getAllPlanes() {
        return planeDao.findByDeletedFalse();
    }

    @Override
    public Plane findByPlaneId(Long planeId) {
        return planeDao.findByPlaneIdAndDeletedFalse(planeId);
    }

    @Override
    public Plane create(PlaneInfo planeInfo) throws LogicException {
        if (ObjectUtils.isEmpty(planeInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL);
        }
        String code = planeInfo.getCode();
        if (!planeInfo.isAllNotNull()) {
            throw new LogicException(ErrorCode.BLANK_FIELD, "Plane's name and code are required");
        }

        Plane plane = new Plane();
        plane.setCode(code);
        plane.setName(planeInfo.getName());
        plane.setCreatedAt(new Date(System.currentTimeMillis()));
        plane.setDeleted(false);

        return planeDao.save(plane);
    }

    @Override
    public Plane update(Long planeId, PlaneInfo planeInfo) throws LogicException {
        if (ObjectUtils.isEmpty(planeInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Plane info is null");
        }
        Plane plane = findByPlaneId(planeId);
        if (ObjectUtils.isEmpty(plane)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Plane does not exist");
        }
        if (!ObjectUtils.isEmpty(planeInfo.getName())) {
            plane.setName(planeInfo.getName());
        }
        if (!ObjectUtils.isEmpty(planeInfo.getCode())) {
            plane.setCode(planeInfo.getCode());
        }
        plane.setUpdatedAt(new Date(System.currentTimeMillis()));
        return planeDao.save(plane);
    }

    @Override
    public ErrorCode delete(Long planeId) {
        Plane plane = findByPlaneId(planeId);
        if (ObjectUtils.isEmpty(plane))
            return ErrorCode.DATA_NULL;
        plane.setDeleted(true);
        plane.setUpdatedAt(new Date(System.currentTimeMillis()));
        return ErrorCode.SUCCESS;
    }

    @Override
    public PlaneInfo getPlaneInfo(Long planeId) {
        Plane plane = findByPlaneId(planeId);
        if (ObjectUtils.isEmpty(plane)) return null;

        PlaneInfo planeInfo = new PlaneInfo();
        planeInfo.setName(plane.getName());
        planeInfo.setCode(plane.getCode());
        return planeInfo;
    }
}
