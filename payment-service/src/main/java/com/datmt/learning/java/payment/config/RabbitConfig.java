package com.datmt.learning.java.payment.config;

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
    public TopicExchange orderExchange() {
        return new TopicExchange(MessagingTopics.Order.EXCHANGE);
    }

    @Bean
    public Queue orderPlacedQueue() {
        return QueueBuilder.durable(MessagingTopics.Order.QUEUE_PAYMENT_ORDER_PLACED).build();
    }

    @Bean
    public Binding bindOrderPlaced() {
        return BindingBuilder.bind(orderPlacedQueue())
                .to(orderExchange())
                .with(MessagingTopics.Order.ROUTING_KEY_ORDER_PLACED);
    }

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(MessagingTopics.Payment.EXCHANGE);
    }
}

