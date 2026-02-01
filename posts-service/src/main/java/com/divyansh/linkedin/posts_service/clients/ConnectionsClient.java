package com.divyansh.linkedin.posts_service.clients;

import com.divyansh.linkedin.posts_service.dto.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "connections-service", path = "/connections")
public interface ConnectionsClient {

    @GetMapping("/core/first-degree")
    List<PersonDTO> getFirstConnections(Long creatorId);

}
