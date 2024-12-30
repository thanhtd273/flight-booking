package com.group1.flight.booking.controller;

import com.group1.flight.booking.core.APIResponse;
import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.ExceptionHandler;
import com.group1.flight.booking.model.Invoice;
import com.group1.flight.booking.service.InvoiceService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flight-booking/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    private final InvoiceService invoiceService;

    @GetMapping()
    public APIResponse getAllInvoices(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Invoice> invoices = invoiceService.getAllInvoices();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, invoices);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/invoices failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/{invoiceId}")
    public APIResponse findByInvoiceId(@PathVariable(value = "invoiceId") Long invoiceId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Invoice invoice = invoiceService.findByInvoiceId(invoiceId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, invoice);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/invoices/{} failed, error: {}", invoiceId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }


    @DeleteMapping(value = "/{invoiceId}/delete")
    public APIResponse deleteInvoice(@PathVariable(value = "invoiceId") Long invoiceId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = invoiceService.delete(invoiceId);
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API DELETE /api/v1/flight-booking/invoices/{}/delete failed, error: {}", invoiceId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
