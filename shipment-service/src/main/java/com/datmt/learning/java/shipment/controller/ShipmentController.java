package com.datmt.learning.java.shipment.controller;

import com.datmt.learning.java.shipment.model.Shipment;
import com.datmt.learning.java.shipment.service.ShipmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService service;

    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Shipment> getAll() {
        return service.getAll();
    }

    @GetMapping("/{orderUlid}")
    public Shipment getByOrderUlid(@PathVariable String orderUlid) {
        return service.getByOrderUlid(orderUlid);
    }
}

