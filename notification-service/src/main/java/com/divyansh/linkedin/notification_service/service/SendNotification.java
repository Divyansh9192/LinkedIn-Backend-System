package com.divyansh.linkedin.notification_service.service;

import com.divyansh.linkedin.notification_service.entity.Notification;
import com.divyansh.linkedin.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendNotification {

    private final NotificationRepository notificationRepository;

    public void send(Long userId, String message){
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUserId(userId);

        notificationRepository.save(notification);

    }
}
