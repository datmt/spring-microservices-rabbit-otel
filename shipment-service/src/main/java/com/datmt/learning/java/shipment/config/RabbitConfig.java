package com.datmt.learning.java.shipment.config;

import com.datmt.learning.java.common.helper.MessagingTopics;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(MessagingTopics.Payment.EXCHANGE);
    }

    @Bean
    public Queue paymentProcessedQueue() {
        return QueueBuilder.durable(MessagingTopics.Payment.QUEUE_SHIPMENT_PAYMENT_PROCESSED).build();
    }

    @Bean
    public Binding bindPaymentProcessed() {
        return BindingBuilder.bind(paymentProcessedQueue())
                .to(paymentExchange())
                .with(MessagingTopics.Payment.ROUTING_KEY_PAYMENT_PROCESSED);
    }

    @Bean
    public TopicExchange shipmentExchange() {
        return new TopicExchange(MessagingTopics.Shipment.EXCHANGE);
    }
}

