package com.arkhanhelskaya.notification.controller;

import com.arkhanhelskaya.notification.repository.NotificationRepository;
import com.arkhanhelskaya.notification.repository.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository repository;

    @GetMapping
    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }
}
