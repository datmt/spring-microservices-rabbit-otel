package com.datmt.learning.java.inventory.service;

import com.datmt.learning.java.common.dto.OrderPlacedEvent;
import com.datmt.learning.java.common.helper.MessagingTopics;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private final InventoryService inventoryService;

    public OrderEventListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RabbitListener(queues = MessagingTopics.Inventory.QUEUE_INVENTORY_ORDER_PLACED)
    public void handleOrderPlaced(OrderPlacedEvent event, Message message) {
        inventoryService.handleOrderPlaced(event);
    }
}

