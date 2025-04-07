package com.datmt.learning.java.order.config;

import com.datmt.learning.java.common.helper.MessagingTopics;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(MessagingTopics.Order.EXCHANGE);
    }
    @Bean
    public TopicExchange inventoryExchange() {
        return new TopicExchange(MessagingTopics.Inventory.EXCHANGE);
    }

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(MessagingTopics.Payment.EXCHANGE);
    }

    @Bean
    public TopicExchange shipmentExchange() {
        return new TopicExchange(MessagingTopics.Shipment.EXCHANGE);
    }
    @Bean
    public Queue inventoryReservedQueue() {
        return QueueBuilder.durable(MessagingTopics.Order.QUEUE_ORDER_INVENTORY_RESERVED).build();
    }

    @Bean
    public Binding bindInventoryReserved(Queue inventoryReservedQueue, TopicExchange inventoryExchange) {
        return BindingBuilder.bind(inventoryReservedQueue)
                .to(inventoryExchange)
                .with(MessagingTopics.Inventory.ROUTING_KEY_INVENTORY_RESERVED);
    }

    @Bean
    public Queue outOfStockQueue() {
        return QueueBuilder.durable(MessagingTopics.Order.QUEUE_ORDER_INVENTORY_OUT_OF_STOCK).build();
    }

    @Bean
    public Binding bindOutOfStock(Queue outOfStockQueue, TopicExchange inventoryExchange) {
        return BindingBuilder.bind(outOfStockQueue)
                .to(inventoryExchange)
                .with(MessagingTopics.Inventory.ROUTING_KEY_OUT_OF_STOCK);
    }

    @Bean
    public Queue paymentProcessedQueue() {
        return QueueBuilder.durable(MessagingTopics.Order.QUEUE_ORDER_PAYMENT_PROCESSED).build();
    }

    @Bean
    public Binding bindPaymentProcessed(Queue paymentProcessedQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(paymentProcessedQueue)
                .to(paymentExchange)
                .with(MessagingTopics.Payment.ROUTING_KEY_PAYMENT_PROCESSED);
    }

    @Bean
    public Queue paymentFailedQueue() {
        return QueueBuilder.durable(MessagingTopics.Order.QUEUE_ORDER_PAYMENT_FAILED).build();
    }

    @Bean
    public Binding bindPaymentFailed(Queue paymentFailedQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(paymentFailedQueue)
                .to(paymentExchange)
                .with(MessagingTopics.Payment.ROUTING_KEY_PAYMENT_FAILED);
    }

    @Bean
    public Queue shipmentCreatedQueue() {
        return QueueBuilder.durable(MessagingTopics.Order.QUEUE_ORDER_SHIPMENT_CREATED).build();
    }

    @Bean
    public Binding bindShipmentCreated(Queue shipmentCreatedQueue, TopicExchange shipmentExchange) {
        return BindingBuilder.bind(shipmentCreatedQueue)
                .to(shipmentExchange)
                .with(MessagingTopics.Shipment.ROUTING_KEY_SHIPMENT_CREATED);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}

