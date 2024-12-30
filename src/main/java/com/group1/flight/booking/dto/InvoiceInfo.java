package com.group1.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvoiceInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    private Long invoiceId;

    private Long bookingId;

    private Float totalAmount;

    @Override
    public String toString() {
        return "InvoiceInfo{" +
                "invoiceId=" + invoiceId +
                ", bookingId=" + bookingId +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
