package com.group5.flight.booking.service;

import com.group5.flight.booking.core.exception.LogicException;
import org.springframework.mail.SimpleMailMessage;

public interface MailService {

    SimpleMailMessage sendSimpleMail(String to, String subject, String text) throws LogicException;

    SimpleMailMessage sendSimpleMail(String[] to, String subject, String text) throws LogicException;
}
