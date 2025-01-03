package com.group1.flight.booking.service.impl;

import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.dao.NationDao;
import com.group1.flight.booking.dto.NationInfo;
import com.group1.flight.booking.model.Nation;
import com.group1.flight.booking.service.NationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NationServiceImpl implements NationService {

    private final NationDao nationDao;

    @Override
    public List<Nation> getAllNations() {
        return nationDao.findAllNations();
    }

    @Override
    public Nation findByNationId(Long nationId) {
        return nationDao.findByNationIdAndDeletedFalse(nationId);
    }

    @Override
    public Nation create(NationInfo nationInfo) throws LogicException {
        if (ObjectUtils.isEmpty(nationInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL);
        }
        if (ObjectUtils.isEmpty(nationInfo.getName())) {
            throw new LogicException(ErrorCode.BLANK_FIELD, "Nation's name is required");
        }
        Nation nation = new Nation();
        nation.setName(nationInfo.getName());
        nation.setCreatedAt(new Date(System.currentTimeMillis()));
        nation.setDeleted(false);
        return nationDao.save(nation);
    }

    @Override
    public Nation update(Long nationId, NationInfo nationInfo) throws LogicException {
        Nation nation = findByNationId(nationId);
        if (ObjectUtils.isEmpty(nation)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Nation does not exist");
        }
        if (!ObjectUtils.isEmpty(nationInfo) && !ObjectUtils.isEmpty(nationInfo.getName())) {
            nation.setName(nationInfo.getName());
            nation.setUpdatedAt(new Date(System.currentTimeMillis()));
            nation = nationDao.save(nation);
        }
        return nation;
    }

    @Override
    public ErrorCode delete(Long nationId) {
        Nation nation = findByNationId(nationId);
        if (ObjectUtils.isEmpty(nation))
            return ErrorCode.DATA_NULL;
        nation.setDeleted(true);
        nation.setUpdatedAt(new Date(System.currentTimeMillis()));
        return ErrorCode.SUCCESS;
    }

    @Override
    public NationInfo getNationInfo(Long nationId) {
        Nation nation = findByNationId(nationId);
        if (ObjectUtils.isEmpty(nation)) return null;

        return new NationInfo(nationId, nation.getName());
    }
}
