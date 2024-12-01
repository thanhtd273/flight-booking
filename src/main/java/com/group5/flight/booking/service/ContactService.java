package com.group5.flight.booking.service;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.ContactInfo;
import com.group5.flight.booking.model.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> getAllContacts();

    Contact findByContactId(Long contactId);
    Contact create(ContactInfo contactInfo) throws LogicException;

    Contact update(Long contactId, ContactInfo contactInfo) throws LogicException;

    ErrorCode delete(Long contactId);

    ContactInfo getContactInfo(Long contactId);

}
