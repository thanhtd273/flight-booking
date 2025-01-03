package com.group1.flight.booking.service.impl;

import com.group1.flight.booking.core.AppUtils;
import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
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

    @Override
    public void sendMailWithAttachment(String to, String subject, String content, File file) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content);

        FileSystemResource fsr = new FileSystemResource(file);
        helper.addAttachment(file.getName(), fsr);

        mailSender.send(message);
    }
}
