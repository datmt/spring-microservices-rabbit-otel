package com.datmt.learning.java.order.config;

import com.datmt.learning.java.common.helper.MessagingTopics;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(MessagingTopics.Order.EXCHANGE);
    }

    @Bean
    public Queue inventoryReservedQueue() {
        return QueueBuilder.durable(MessagingTopics.Inventory.QUEUE_ORDER_INVENTORY_RESERVED).build();
    }

    @Bean
    public Binding bindInventoryReserved() {
        return BindingBuilder.bind(inventoryReservedQueue())
                .to(new TopicExchange(MessagingTopics.Inventory.EXCHANGE))
                .with(MessagingTopics.Inventory.ROUTING_KEY_INVENTORY_RESERVED);
    }

    @Bean
    public Queue outOfStockQueue() {
        return QueueBuilder.durable(MessagingTopics.Inventory.QUEUE_ORDER_OUT_OF_STOCK).build();
    }

    @Bean
    public Binding bindOutOfStock() {
        return BindingBuilder.bind(outOfStockQueue())
                .to(new TopicExchange(MessagingTopics.Inventory.EXCHANGE))
                .with(MessagingTopics.Inventory.ROUTING_KEY_OUT_OF_STOCK);
    }

    @Bean
    public Queue paymentProcessedQueue() {
        return QueueBuilder.durable(MessagingTopics.Payment.QUEUE_ORDER_PAYMENT_PROCESSED).build();
    }

    @Bean
    public Binding bindPaymentProcessed() {
        return BindingBuilder.bind(paymentProcessedQueue())
                .to(new TopicExchange(MessagingTopics.Payment.EXCHANGE))
                .with(MessagingTopics.Payment.ROUTING_KEY_PAYMENT_PROCESSED);
    }

    @Bean
    public Queue paymentFailedQueue() {
        return QueueBuilder.durable(MessagingTopics.Payment.QUEUE_ORDER_PAYMENT_FAILED).build();
    }

    @Bean
    public Binding bindPaymentFailed() {
        return BindingBuilder.bind(paymentFailedQueue())
                .to(new TopicExchange(MessagingTopics.Payment.EXCHANGE))
                .with(MessagingTopics.Payment.ROUTING_KEY_PAYMENT_FAILED);
    }

    @Bean
    public Queue shipmentCreatedQueue() {
        return QueueBuilder.durable(MessagingTopics.Shipment.QUEUE_ORDER_SHIPMENT_CREATED).build();
    }

    @Bean
    public Binding bindShipmentCreated() {
        return BindingBuilder.bind(shipmentCreatedQueue())
                .to(new TopicExchange(MessagingTopics.Shipment.EXCHANGE))
                .with(MessagingTopics.Shipment.ROUTING_KEY_SHIPMENT_CREATED);
    }

    @Bean
    public Queue productUpdatedQueue() {
        return QueueBuilder.durable(MessagingTopics.Catalog.QUEUE_ORDER_PRODUCT_UPDATED).build();
    }

    @Bean
    public Binding bindProductUpdated() {
        return BindingBuilder.bind(productUpdatedQueue())
                .to(new TopicExchange(MessagingTopics.Catalog.EXCHANGE))
                .with(MessagingTopics.Catalog.ROUTING_KEY_PRODUCT_UPDATED);
    }
}

