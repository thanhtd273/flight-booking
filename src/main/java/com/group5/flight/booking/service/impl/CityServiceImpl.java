package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.CityDao;
import com.group5.flight.booking.dto.CityInfo;
import com.group5.flight.booking.dto.NationInfo;
import com.group5.flight.booking.model.City;
import com.group5.flight.booking.model.Nation;
import com.group5.flight.booking.service.CityService;
import com.group5.flight.booking.service.NationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityDao cityDao;

    private final NationService nationService;

    @Override
    public List<City> getAllCities() {
        return cityDao.findByDeletedFalse();
    }

    @Override
    public City findByCityId(Long cityId) {
        return cityDao.findByCityIdAndDeletedFalse(cityId);
    }

    @Override
    public City create(CityInfo cityInfo) throws LogicException {
        if (ObjectUtils.isEmpty(cityInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL);
        }
        Long nationId = cityInfo.getNationId();
        if (ObjectUtils.isEmpty(cityInfo.getName()) || ObjectUtils.isEmpty(nationId)) {
            throw new LogicException(ErrorCode.BLANK_FIELD, "City's name and nation id is required");
        }

        Nation nation = nationService.findByNationId(nationId);
        if (ObjectUtils.isEmpty(nation)) {
            throw new LogicException(ErrorCode.DATA_NULL, "City does not exist");
        }
        City city = new City();
        city.setNationId(nationId);
        city.setName(cityInfo.getName());
        city.setCreatedAt(new Date(System.currentTimeMillis()));
        city.setDeleted(false);

        return cityDao.save(city);
    }

    @Override
    public City update(Long cityId, CityInfo cityInfo) throws LogicException {
        if (ObjectUtils.isEmpty(cityInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL, "City info is null");
        }
        City city = findByCityId(cityId);
        if (ObjectUtils.isEmpty(city)) {
            throw new LogicException(ErrorCode.DATA_NULL, "City does not exist");
        }
        if (!ObjectUtils.isEmpty(cityInfo.getName())) {
            city.setName(cityInfo.getName());
        }
        if (!ObjectUtils.isEmpty(cityInfo.getNationId())) {
            city.setNationId(cityInfo.getNationId());
        }
        city.setUpdatedAt(new Date(System.currentTimeMillis()));
        return cityDao.save(city);
    }

    @Override
    public ErrorCode delete(Long cityId) {
        City city = findByCityId(cityId);
        if (ObjectUtils.isEmpty(city))
            return ErrorCode.DATA_NULL;
        city.setDeleted(true);
        city.setUpdatedAt(new Date(System.currentTimeMillis()));
        return ErrorCode.SUCCESS;
    }

    @Override
    public CityInfo getCityInfo(Long cityId) {
        City city = findByCityId(cityId);
        if (ObjectUtils.isEmpty(city)) return null;

        NationInfo nationInfo = nationService.getNationInfo(city.getNationId());
        return new CityInfo(cityId, city.getNationId(), nationInfo, city.getName());
    }
}
