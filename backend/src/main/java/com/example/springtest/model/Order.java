package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;
import java.util.List;

@Node("Order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(GeneratedValue.UUIDGenerator.class)
    private String id;

    private Float price;

    private String status;

    private Date creationDate;

    private Date editDate;

    @Relationship(type = "ORDERED_BY", direction = Relationship.Direction.OUTGOING)
    private Client client;

    @Relationship(type = "CONTAINS", direction = Relationship.Direction.OUTGOING)
    private List<Service> services;

    @Relationship(type = "EXECUTED_BY", direction = Relationship.Direction.OUTGOING)
    private Branch branch;
}