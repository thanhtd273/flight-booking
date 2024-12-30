package com.group1.flight.booking.service;

import com.group1.flight.booking.core.exception.LogicException;
import org.springframework.mail.SimpleMailMessage;

public interface MailService {

    SimpleMailMessage sendSimpleMail(String to, String subject, String text) throws LogicException;

    SimpleMailMessage sendSimpleMail(String[] to, String subject, String text) throws LogicException;
}
