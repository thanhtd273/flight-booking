package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvoiceInfo {

    private Long invoiceId;

    private Long contactId;

    private ContactInfo contact;

    private List<PassengerInfo> passengerInfos;

    private Float totalAmount;

}
