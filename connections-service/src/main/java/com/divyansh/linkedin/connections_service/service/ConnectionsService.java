package com.divyansh.linkedin.connections_service.service;

import com.divyansh.linkedin.connections_service.auth.UserContextHolder;
import com.divyansh.linkedin.connections_service.entity.Person;
import com.divyansh.linkedin.connections_service.event.AcceptConnectionRequestEvent;
import com.divyansh.linkedin.connections_service.event.SendConnectionRequestEvent;
import com.divyansh.linkedin.connections_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.data.neo4j.repository.support.SimpleNeo4jRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, Object> kafkaTemplate;


    public List<Person> getFirstDegreeConnections(){
         Long userId = UserContextHolder.getCurrentUserId();
         log.info("Getting First Degree Connections for user with id : {}",userId);
         return personRepository.getFirstDegreeConnections(userId);
     }

    public Boolean sendConnectionRequest(Long receiverId) {
         Long senderId = UserContextHolder.getCurrentUserId();
         log.info("Trying to send connection request, sender: {}, receiver: {}",senderId,receiverId);

         if(senderId.equals(receiverId)){
             throw new RuntimeException("Both sender and receiver are the same");
         }

         boolean alreadySentRequest = personRepository.connectionRequestExists(senderId,receiverId);
         if(alreadySentRequest){
             throw new RuntimeException("Connection request already exists, cannot send again!");
         }

         boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);
        if(alreadyConnected){
            throw new RuntimeException("Already Connected users, cannot add connection request");
        }

        personRepository.addConnectionRequest(senderId,receiverId);
        log.info("Successfully sent the connection request");

        SendConnectionRequestEvent sendConnectionRequestEvent = SendConnectionRequestEvent.builder()
                .receiverId(receiverId)
                .senderId(senderId)
                .build();
        kafkaTemplate.send("send-connection-request-topic",senderId,sendConnectionRequestEvent);
        return true;
     }


    public Boolean acceptConnectionRequest(Long receiverId) {
         Long senderId = UserContextHolder.getCurrentUserId();
         boolean havingConnectionRequest = personRepository.connectionRequestExists(senderId,receiverId);
         if (!havingConnectionRequest){
             throw new RuntimeException("You can't accept a connection without the request");
         }
         personRepository.addConnection(senderId,receiverId);
         log.info("Successfully accepted the connection request, sender: {}, receiver: {}",senderId,receiverId);
        AcceptConnectionRequestEvent acceptConnectionRequestEvent = AcceptConnectionRequestEvent.builder()
                .receiverId(receiverId)
                .senderId(senderId)
                .build();
        kafkaTemplate.send("accept-connection-request-topic",senderId,acceptConnectionRequestEvent);
         return true;
    }

    public Boolean rejectConnectionRequest(Long receiverId) {
        Long senderId = UserContextHolder.getCurrentUserId();
        boolean havingConnectionRequest = personRepository.connectionRequestExists(senderId,receiverId);
        if (!havingConnectionRequest){
            throw new RuntimeException("You can't reject a connection without the request");
        }
        personRepository.rejectConnection(senderId,receiverId);
        return true;
    }
}
