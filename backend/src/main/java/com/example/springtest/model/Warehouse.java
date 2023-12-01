package com.example.springtest.model;


import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Warehouse")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Warehouse {
    @Relationship(type = "STORE", direction = Relationship.Direction.OUTGOING)
    private Product product;

    @Relationship(type = "STORE", direction = Relationship.Direction.OUTGOING)
    private Employee employee;

    @Relationship(type = "MANAGE", direction = Relationship.Direction.INCOMING)
    private User user;

}
