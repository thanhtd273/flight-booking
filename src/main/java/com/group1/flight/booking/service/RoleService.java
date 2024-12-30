package com.group1.flight.booking.service;


import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.model.Role;
import com.group1.flight.booking.dto.RoleInfo;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role findByRoleId(Long roleId) throws LogicException;

    Role findByName(String name) throws LogicException;

    List<Role> findByUserId(Long userId) throws LogicException;

    Role createRole(RoleInfo roleInfo) throws LogicException;

    Role updateRole(Long roleId, RoleInfo roleInfo) throws LogicException;

    ErrorCode deleteRole(Long roleId);
}
