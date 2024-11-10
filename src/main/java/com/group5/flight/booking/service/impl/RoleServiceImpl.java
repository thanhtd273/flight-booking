package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.DataStatus;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.RoleDao;
import com.group5.flight.booking.dao.UserRoleDao;
import com.group5.flight.booking.dto.RoleInfo;
import com.group5.flight.booking.model.Role;
import com.group5.flight.booking.model.UserRole;
import com.group5.flight.booking.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    private final UserRoleDao userRoleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role findByRoleId(Long roleId) throws LogicException {
        if (ObjectUtils.isEmpty(roleId))
            throw new LogicException(ErrorCode.ID_NULL);

        return roleDao.findByRoleId(roleId);
    }

    @Override
    public Role findByName(String name) throws LogicException {
        return roleDao.findByName(name);
    }

    @Override
    public List<Role> findByUserId(Long userId) throws LogicException {
        List<Role> roles = new ArrayList<>();
        List<UserRole> userRoles = userRoleDao.findByUserId(userId);
        for (UserRole userRole : userRoles) {
            Role role = findByRoleId(userRole.getRoleId());
            roles.add(role);
        }
        return roles;
    }

    @Override
    public Role createRole(RoleInfo roleInfo) throws LogicException {
        if (ObjectUtils.isEmpty(roleInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (ObjectUtils.isEmpty(roleInfo.getName()))
            throw new LogicException(ErrorCode.BLANK_FIELD);

        Role role = new Role();
        role.setName(roleInfo.getName());
        if (!ObjectUtils.isEmpty(roleInfo.getDescription()))
            role.setDescription(roleInfo.getDescription());
        if (!ObjectUtils.isEmpty(roleInfo.getStatus()))
            role.setStatus(roleInfo.getStatus());

        role.setCreateDate(new Date());
        role.setStatus(DataStatus.ACTIVE);
        role = roleDao.save(role);

        return role;
    }

    @Override
    public Role updateRole(Long roleId, RoleInfo roleInfo) throws LogicException {
        if (ObjectUtils.isEmpty(roleInfo) || ObjectUtils.isEmpty(roleId))
            throw new LogicException(ErrorCode.DATA_NULL);

        Role role = roleDao.findByRoleId(roleId);
        if (ObjectUtils.isEmpty(role))
            throw new LogicException(ErrorCode.DATA_NULL);

        if (!ObjectUtils.isEmpty(roleInfo.getName()))
            role.setName(roleInfo.getName());
        if (!ObjectUtils.isEmpty(roleInfo.getDescription())) {
            role.setDescription(roleInfo.getDescription());
        }
        if (!ObjectUtils.isEmpty(roleInfo.getStatus())) {
            role.setStatus(roleInfo.getStatus());
        }
        role.setModifiedDate(new Date());
        role = roleDao.save(role);

        return role;
    }

    @Override
    public ErrorCode deleteRole(Long roleId) {
        if (ObjectUtils.isEmpty(roleId))
            return ErrorCode.ID_NULL;
        Role role = roleDao.findByRoleId(roleId);
        if (ObjectUtils.isEmpty(role))
            return ErrorCode.DATA_NULL;
        role.setModifiedDate(new Date());
        role.setStatus(DataStatus.DELETED);
        return ErrorCode.SUCCESS;
    }

}
