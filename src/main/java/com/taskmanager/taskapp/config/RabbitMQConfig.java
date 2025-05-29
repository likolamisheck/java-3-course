package com.taskmanager.taskapp.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String TASK_QUEUE = "task_queue";
    public static final String TASK_EXCHANGE = "task_exchange";
    public static final String TASK_ROUTING_KEY = "task_routing_key";

    @Bean
    public Queue queue() {
        return new Queue(TASK_QUEUE, false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(TASK_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TASK_ROUTING_KEY);
    }
}
