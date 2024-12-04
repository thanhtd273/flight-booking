package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactDao extends JpaRepository<Contact, Long> {
    List<Contact> findByDeletedFalse();

    Contact findByContactIdAndDeletedFalse(Long contactId);
}
