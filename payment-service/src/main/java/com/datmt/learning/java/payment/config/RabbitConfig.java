package com.datmt.learning.java.payment.config;

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
    public TopicExchange inventoryExchange() {
        return new TopicExchange(MessagingTopics.Inventory.EXCHANGE);
    }
    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(MessagingTopics.Payment.EXCHANGE);
    }
    @Bean
    public Queue inventoryReservedQueue() {
        return QueueBuilder.durable(MessagingTopics.Payment.QUEUE_PAYMENT_INVENTORY_RESERVED).build();
    }

    @Bean
    public Binding bindOrderPlaced(Queue inventoryReservedQueue, TopicExchange inventoryExchange) {
        return BindingBuilder.bind(inventoryReservedQueue)
                .to(inventoryExchange)
                .with(MessagingTopics.Inventory.ROUTING_KEY_INVENTORY_RESERVED);
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

