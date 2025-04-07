package com.datmt.learning.java.order.service;

import com.datmt.learning.java.common.dto.*;
import com.datmt.learning.java.common.helper.MessagingTopics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private final Logger log = LoggerFactory.getLogger(OrderEventListener.class);
    private final OrderService orderService;
    public OrderEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = MessagingTopics.Order.QUEUE_ORDER_INVENTORY_RESERVED)
    public void handleInventoryReserved(InventoryReservedEvent event, Message message) {
        log.info("Received InventoryReservedEvent for order {}", event.orderUlid());
        orderService.handleInventoryReserved(event);
    }

    @RabbitListener(queues = MessagingTopics.Order.QUEUE_ORDER_INVENTORY_OUT_OF_STOCK)
    public void handleInventoryOutOfStock(InventoryOutOfStockEvent event, Message message) {
        log.info("Received InventoryOutOfStockEvent for order {}", event.orderUlid());
        orderService.handleInventoryOutOfStock(event);
    }

    @RabbitListener(queues = MessagingTopics.Order.QUEUE_ORDER_PAYMENT_PROCESSED)
    public void handlePaymentProcessed(PaymentProcessedEvent event, Message message) {
        log.info("Received PaymentProcessedEvent for order {}", event.orderUlid());
        orderService.handlePaymentProcessed(event);
    }

    @RabbitListener(queues = MessagingTopics.Order.QUEUE_ORDER_PAYMENT_FAILED)
    public void handlePaymentFailed(PaymentFailedEvent event, Message message) {
        log.info("Received PaymentFailedEvent for order {}", event.orderUlid());
        orderService.handlePaymentFailed(event);
    }

    @RabbitListener(queues = MessagingTopics.Order.QUEUE_ORDER_SHIPMENT_CREATED)
    public void handleOrderShipped(OrderShippedEvent event, Message message) {
        log.info("Received OrderShippedEvent for order {}", event.orderUlid());
        orderService.handleOrderShipped(event);
    }
}
