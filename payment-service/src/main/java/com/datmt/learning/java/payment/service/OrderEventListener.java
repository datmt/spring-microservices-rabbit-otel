package com.datmt.learning.java.payment.service;

import com.datmt.learning.java.common.dto.OrderPlacedEvent;
import com.datmt.learning.java.common.helper.MessagingTopics;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private final PaymentService paymentService;

    public OrderEventListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = MessagingTopics.Order.QUEUE_PAYMENT_ORDER_PLACED)
    public void onOrderPlaced(OrderPlacedEvent event) {
        paymentService.processOrder(event);
    }
}
