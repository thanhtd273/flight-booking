package com.group1.flight.booking.service.impl;

import com.group1.flight.booking.core.AppUtils;
import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Override
    public SimpleMailMessage sendSimpleMail(String to, String subject, String text) throws LogicException {
        // Validate destination email
        if (Boolean.FALSE.equals(AppUtils.validateEmail(to)))
            throw new LogicException(ErrorCode.INVALID_EMAIL);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(text);
        message.setTo(to);
        message.setSubject(subject);
        mailSender.send(message);
        return message;
    }

    @Override
    public SimpleMailMessage sendSimpleMail(String[] to, String subject, String text) throws LogicException{
        // Validate destination emails
        if (Arrays.stream(to).anyMatch(email -> Boolean.FALSE.equals(AppUtils.validateEmail(email)))) {
            throw new LogicException(ErrorCode.INVALID_EMAIL);
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(text);
        message.setTo(to);
        message.setSubject(subject);
        mailSender.send(message);
        return message;
    }
}
