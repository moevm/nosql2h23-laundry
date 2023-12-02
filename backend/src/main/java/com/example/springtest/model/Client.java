package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.List;

@Node("Client")
@Getter
@Setter
public class Client extends User {
    @Relationship(type = "ORDERED_BY", direction = Relationship.Direction.INCOMING)
    private List<Order> orders;
}
