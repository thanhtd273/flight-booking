package com.group1.flight.booking.service;

import com.group1.flight.booking.core.exception.LogicException;
import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;

import java.io.File;

public interface MailService {

    SimpleMailMessage sendSimpleMail(String to, String subject, String text) throws LogicException;

    SimpleMailMessage sendSimpleMail(String[] to, String subject, String text) throws LogicException;

    void sendMailWithAttachment(String to, String subject, String text, File[] file) throws MessagingException;
}
