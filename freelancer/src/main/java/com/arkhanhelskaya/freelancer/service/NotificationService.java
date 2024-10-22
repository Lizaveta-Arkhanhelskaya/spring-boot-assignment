package com.arkhanhelskaya.freelancer.service;

import com.arkhanhelskaya.freelancer.repository.entity.Freelancer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Freelancer freelancer) {
        rabbitTemplate.convertAndSend(freelancer.toString());
    }
}
