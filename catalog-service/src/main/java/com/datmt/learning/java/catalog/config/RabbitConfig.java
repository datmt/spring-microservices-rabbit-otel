package com.datmt.learning.java.catalog.config;

import com.datmt.learning.java.common.helper.MessagingTopics;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange catalogExchange() {
        return new TopicExchange(MessagingTopics.Catalog.EXCHANGE);
    }
}

