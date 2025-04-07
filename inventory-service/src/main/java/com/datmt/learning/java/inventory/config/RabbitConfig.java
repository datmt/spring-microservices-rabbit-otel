package com.datmt.learning.java.inventory.config;

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
    public Queue orderPlacedQueue() {
        return QueueBuilder.durable(MessagingTopics.Order.QUEUE_INVENTORY_ORDER_PLACED).build();
    }

    @Bean
    public Binding bindOrderPlaced() {
        return BindingBuilder.bind(orderPlacedQueue())
                .to(orderExchange())
                .with(MessagingTopics.Order.ROUTING_KEY_ORDER_PLACED);
    }

    @Bean
    public TopicExchange inventoryExchange() {
        return new TopicExchange(MessagingTopics.Inventory.EXCHANGE);
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

