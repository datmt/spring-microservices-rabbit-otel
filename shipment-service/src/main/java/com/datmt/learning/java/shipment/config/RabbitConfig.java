package com.datmt.learning.java.shipment.config;

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
    public TopicExchange paymentExchange() {
        return new TopicExchange(MessagingTopics.Payment.EXCHANGE);
    }

    @Bean
    public TopicExchange shipmentExchange() {
        return new TopicExchange(MessagingTopics.Shipment.EXCHANGE);
    }

    @Bean
    public Queue paymentProcessedQueue() {
        return QueueBuilder.durable(MessagingTopics.Shipment.QUEUE_SHIPMENT_PAYMENT_PROCESSED).build();
    }

    @Bean
    public Binding bindPaymentProcessed(Queue paymentProcessedQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(paymentProcessedQueue)
                .to(paymentExchange)
                .with(MessagingTopics.Payment.ROUTING_KEY_PAYMENT_PROCESSED);
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

