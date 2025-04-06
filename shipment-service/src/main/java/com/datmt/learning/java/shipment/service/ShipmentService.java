package com.datmt.learning.java.shipment.service;

import com.datmt.learning.java.common.dto.OrderShippedEvent;
import com.datmt.learning.java.common.dto.PaymentProcessedEvent;
import com.datmt.learning.java.common.helper.MessagingTopics;
import com.datmt.learning.java.shipment.model.Shipment;
import com.datmt.learning.java.shipment.model.ShipmentStatus;
import com.datmt.learning.java.shipment.repository.ShipmentRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ShipmentService {

    private final ShipmentRepository repo;
    private final RabbitTemplate rabbitTemplate;

    public ShipmentService(ShipmentRepository repo, RabbitTemplate rabbitTemplate) {
        this.repo = repo;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Shipment> getAll() {
        return repo.findAll();
    }

    public Shipment getByOrderUlid(String orderUlid) {
        return repo.findByOrderUlid(orderUlid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        String tracking = generateTrackingNumber();

        Shipment shipment = new Shipment(
                event.orderUlid(),
                tracking,
                ShipmentStatus.SHIPPED
        );

        shipment = repo.save(shipment);

        OrderShippedEvent shippedEvent = new OrderShippedEvent(event.orderUlid(), tracking);
        rabbitTemplate.convertAndSend(
                MessagingTopics.Shipment.EXCHANGE,
                MessagingTopics.Shipment.ROUTING_KEY_SHIPMENT_CREATED,
                shippedEvent
        );
    }

    private String generateTrackingNumber() {
        return "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

