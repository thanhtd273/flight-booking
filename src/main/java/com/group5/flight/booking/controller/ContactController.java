package com.group5.flight.booking.controller;

import com.group5.flight.booking.core.APIResponse;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.ExceptionHandler;
import com.group5.flight.booking.dto.ContactInfo;
import com.group5.flight.booking.model.Contact;
import com.group5.flight.booking.service.ContactService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flight-booking/contacts")
@RequiredArgsConstructor
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;

    @GetMapping()
    public APIResponse getAllContacts(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Contact> contacts = contactService.getAllContacts();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, contacts);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/contacts failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/{contactId}")
    public APIResponse findByContactId(@PathVariable(value = "contactId") Long contactId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Contact contact = contactService.findByContactId(contactId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, contact);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/contacts/{} failed, error: {}", contactId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping(value = "/create")
    public APIResponse createContact(@RequestBody ContactInfo contactInfo, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Contact contact = contactService.create(contactInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, contact);
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/contacts/create failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PutMapping(value = "/{contactId}/update")
    public APIResponse updateContact(@PathVariable(value = "contactId") Long contactId, @RequestBody ContactInfo contactInfo,
                                       HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Contact contact = contactService.update(contactId, contactInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, contact);
        } catch (Exception e) {
            logger.error("Call API PUT /api/v1/flight-booking/contacts/{}/update failed, error: {}", contactId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @DeleteMapping(value = "/{contactId}/delete")
    public APIResponse deleteContact(@PathVariable(value = "contactId") Long contactId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = contactService.delete(contactId);
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API DELETE /api/v1/flight-booking/contacts/{}/delete failed, error: {}", contactId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
