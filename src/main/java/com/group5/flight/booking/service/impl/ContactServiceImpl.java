package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.ContactDao;
import com.group5.flight.booking.dto.ContactInfo;
import com.group5.flight.booking.model.Contact;
import com.group5.flight.booking.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactDao contactDao;


    @Override
    public List<Contact> getAllContacts() {
        return contactDao.findByDeletedFalse();
    }

    @Override
    public Contact findByContactId(Long contactId) {
        return contactDao.findByContactIdAndDeletedFalse(contactId);
    }

    @Override
    public Contact create(ContactInfo contactInfo) throws LogicException {
        if (ObjectUtils.isEmpty(contactInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL);
        }
        if (!contactInfo.isAllNull()) {
            throw new LogicException(ErrorCode.BLANK_FIELD, "Contact's info is required");
        }

        Contact contact = new Contact();
        contact.setFirstName(contactInfo.getFirstName());
        contact.setLastName(contactInfo.getLastName());
        contact.setPhone(contactInfo.getPhone());
        contact.setEmail(contactInfo.getEmail());

        contact.setCreatedAt(new Date(System.currentTimeMillis()));
        contact.setDeleted(false);

        return contactDao.save(contact);
    }

    @Override
    public Contact update(Long contactId, ContactInfo contactInfo) throws LogicException {
        if (ObjectUtils.isEmpty(contactInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Contact info is null");
        }
        Contact contact = findByContactId(contactId);
        if (ObjectUtils.isEmpty(contact)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Contact does not exist");
        }
        if (!ObjectUtils.isEmpty(contactInfo.getFirstName())) {
            contact.setFirstName(contactInfo.getFirstName());
        }
        if (!ObjectUtils.isEmpty(contactInfo.getLastName())) {
            contact.setLastName(contactInfo.getLastName());
        }
        if (!ObjectUtils.isEmpty(contactInfo.getPhone())) {
            contact.setPhone(contactInfo.getPhone());
        }
        if (!ObjectUtils.isEmpty(contactInfo.getEmail())) {
            contact.setEmail(contactInfo.getEmail());
        }

        contact.setUpdatedAt(new Date(System.currentTimeMillis()));
        return contactDao.save(contact);
    }

    @Override
    public ErrorCode delete(Long contactId) {
        Contact contact = findByContactId(contactId);
        if (ObjectUtils.isEmpty(contact))
            return ErrorCode.DATA_NULL;
        contact.setDeleted(true);
        contact.setUpdatedAt(new Date(System.currentTimeMillis()));
        return ErrorCode.SUCCESS;
    }

    @Override
    public ContactInfo getContactInfo(Long contactId) {
        Contact contact = findByContactId(contactId);
        if (ObjectUtils.isEmpty(contact)) return null;

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setFirstName(contact.getFirstName());
        contactInfo.setLastName(contact.getLastName());
        contactInfo.setEmail(contact.getEmail());
        contactInfo.setPhone(contact.getPhone());


        return contactInfo;
    }
}
