package com.arkhanhelskaya.notification.rabbitmq;

import com.arkhanhelskaya.notification.repository.NotificationRepository;
import com.arkhanhelskaya.notification.repository.entity.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RabbitMQConsumerService {

    @Autowired
    private NotificationRepository repository;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(String message) {
        var entity = new Notification();
        entity.setMessage(message);
        entity.setCreatedAt(Instant.now());
        repository.save(entity);
    }
}
