package com.group5.flight.booking.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatInfo {
    private Long seatId;
    private String classLevel;
    private String seatCode;
    private Boolean available;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
