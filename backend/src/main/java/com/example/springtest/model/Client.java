package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;
import java.util.List;

@Node("Client")
@Getter
@Setter
public class Client extends User {

    @Relationship(type = "ORDERED_BY", direction = Relationship.Direction.INCOMING)
    private List<Order> orders;

    @Builder
    public Client(String id, String password, String fullName, String email, Date creationDate, Date editDate, List<Order> orders) {
        super(id, password, fullName, email, creationDate, editDate);
        this.orders = orders;
    }
}
