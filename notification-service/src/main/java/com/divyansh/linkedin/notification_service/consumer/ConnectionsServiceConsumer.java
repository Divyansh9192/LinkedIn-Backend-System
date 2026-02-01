package com.divyansh.linkedin.notification_service.consumer;

import com.divyansh.linkedin.connections_service.event.AcceptConnectionRequestEvent;
import com.divyansh.linkedin.connections_service.event.SendConnectionRequestEvent;
import com.divyansh.linkedin.notification_service.service.SendNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsServiceConsumer {

    private final SendNotification sendNotification;

    @KafkaListener(topics = "send-connection-request-topic")
    public void handleSendConnectionRequest(SendConnectionRequestEvent sendConnectionRequestEvent){
        log.info("handle Connections: handleSendConnectionRequest: {}",sendConnectionRequestEvent);
        String message = "You have received a connection request from user with id:"+sendConnectionRequestEvent.getSenderId();

        sendNotification.send(sendConnectionRequestEvent.getReceiverId(),message);
    }

    @KafkaListener(topics = "accept-connection-request-topic")
    public void handleAcceptConnectionRequest(AcceptConnectionRequestEvent acceptConnectionRequestEvent){
        String message = "You're connection request has been accepted by user with id:"+acceptConnectionRequestEvent.getReceiverId();

        sendNotification.send(acceptConnectionRequestEvent.getSenderId(),message);
    }
}
