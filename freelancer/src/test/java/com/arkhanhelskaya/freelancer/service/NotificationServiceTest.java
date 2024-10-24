package com.arkhanhelskaya.freelancer.service;

import com.arkhanhelskaya.freelancer.model.EventType;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class NotificationServiceTest {

    @InjectMocks
    private NotificationService testee;

    @Mock
    private RabbitTemplate rabbitTemplate;

    public static final String QUEUE_NAME = "queueName";

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(testee, "queueName", QUEUE_NAME);
    }

    @Test
    public void sendMessage(){
        var id = UUID.fromString("e18d9303-861d-459a-a94f-9dc30060d73d");
        var event = EventType.CREATED;

        testee.sendMessage(id, event);

        verify(rabbitTemplate).convertAndSend(eq(QUEUE_NAME),
                eq("Freelancer e18d9303-861d-459a-a94f-9dc30060d73d is CREATED"));
    }
}
