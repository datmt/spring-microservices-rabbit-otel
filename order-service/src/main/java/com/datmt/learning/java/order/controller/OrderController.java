package com.datmt.learning.java.order.controller;

import com.datmt.learning.java.order.dto.CreateOrderRequest;
import com.datmt.learning.java.order.dto.OrderDTO;
import com.datmt.learning.java.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody CreateOrderRequest request) {
        OrderDTO dto = service.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public List<OrderDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{ulid}")
    public OrderDTO getByUlid(@PathVariable String ulid) {
        return service.findByUlid(ulid);
    }

    @DeleteMapping("/{ulid}")
    public ResponseEntity<Void> delete(@PathVariable String ulid) {
        service.deleteByUlid(ulid);
        return ResponseEntity.noContent().build();
    }
}
