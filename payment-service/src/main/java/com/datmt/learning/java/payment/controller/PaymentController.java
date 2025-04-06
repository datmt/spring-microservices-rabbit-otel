package com.datmt.learning.java.payment.controller;

import com.datmt.learning.java.payment.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.datmt.learning.java.payment.model.Payment;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Payment> getAll() {
        return service.getAll();
    }

    @GetMapping("/{orderUlid}")
    public Payment getByOrderUlid(@PathVariable String orderUlid) {
        return service.getByOrderUlid(orderUlid);
    }
}

