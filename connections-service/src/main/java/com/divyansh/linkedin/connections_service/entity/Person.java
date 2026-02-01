package com.divyansh.linkedin.connections_service.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
@Data
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private String name;

    @Relationship(type = "CONNECTED_TO", direction = Relationship.Direction.OUTGOING)
    private Set<Person> connections;
}
