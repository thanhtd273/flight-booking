package com.group1.flight.booking.service;

import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.dto.BookingDetail;
import com.group1.flight.booking.dto.BookingInfo;
import com.group1.flight.booking.model.Booking;

import java.util.List;

public interface BookingService {

    List<Booking> getAllBookings();

    Booking findByBookingId(Long bookingId) throws LogicException;

    Booking create(BookingInfo bookingInfo) throws LogicException;

    Booking fillOutBookingDetail(Long bookingId, BookingDetail bookingDetail) throws LogicException;

    ErrorCode delete(Long bookingId) throws LogicException;

    BookingInfo getBookingInfo(Long bookingId) throws LogicException;

    ErrorCode exportAndSendOnlineTicket(Long bookingId) throws LogicException;

    ErrorCode pay(Long bookingId) throws LogicException;
}
