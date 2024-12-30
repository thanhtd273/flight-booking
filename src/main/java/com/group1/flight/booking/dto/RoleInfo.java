package com.group1.flight.booking.dto;

import lombok.Data;

@Data
public class RoleInfo {
    private Long roleId;

    private String name;

    private String description;

    private Integer status;

    private String permissions;
}
