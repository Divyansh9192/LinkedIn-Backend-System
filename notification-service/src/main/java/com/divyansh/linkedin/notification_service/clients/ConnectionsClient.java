package com.divyansh.linkedin.notification_service.clients;

import com.divyansh.linkedin.notification_service.dto.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "connections-service", path = "/connections")
public interface ConnectionsClient {

    @GetMapping("/core/first-degree")
    List<PersonDTO> getFirstConnections(@RequestHeader("X-User-Id") Long userId);

}
