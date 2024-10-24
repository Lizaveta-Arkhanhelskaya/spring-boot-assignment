package com.arkhanhelskaya.notification.rabbitmq;

import com.arkhanhelskaya.notification.repository.NotificationRepository;
import com.arkhanhelskaya.notification.repository.entity.Notification;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitMQConsumerServiceTest {

    @InjectMocks
    private RabbitMQConsumerService testee;
    @Mock
    private NotificationRepository repository;

    @Test
    public void receiveMessage(){
        var message = "some message";

        testee.receiveMessage(message);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(repository).save(captor.capture());

        var notification = captor.getValue();
        assertNotNull(notification);
        assertEquals(message, notification.getMessage());
        assertNotNull(notification.getCreatedAt());
    }
}
