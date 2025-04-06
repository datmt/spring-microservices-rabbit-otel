package com.datmt.learning.java.shipment.service;

import com.datmt.learning.java.common.dto.PaymentProcessedEvent;
import com.datmt.learning.java.common.helper.MessagingTopics;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {

    private final ShipmentService shipmentService;

    public PaymentEventListener(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @RabbitListener(queues = MessagingTopics.Payment.QUEUE_SHIPMENT_PAYMENT_PROCESSED)
    public void onPaymentProcessed(PaymentProcessedEvent event) {
        shipmentService.handlePaymentProcessed(event);
    }
}

