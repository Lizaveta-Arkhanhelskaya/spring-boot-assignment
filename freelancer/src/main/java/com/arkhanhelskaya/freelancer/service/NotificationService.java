package com.arkhanhelskaya.freelancer.service;

import com.arkhanhelskaya.freelancer.model.EventType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String queueName;

    public void sendMessage(UUID id, EventType event) {
        var message = generateMessage(id, event);
        rabbitTemplate.convertAndSend(queueName, message);
    }

    private String generateMessage(UUID id, EventType event){
        return String.format("Freelancer %s is %s", id, event);
    }
}
