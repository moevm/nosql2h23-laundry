package com.example.springtest.model;

import com.example.springtest.model.relationships.Contains;
import com.example.springtest.model.types.OrderState;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Node("Order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Order {

    @Id
    private UUID id;

    private float price;

    private OrderState state;

    private ZonedDateTime creationDate;

    private ZonedDateTime editDate;

    @Relationship(type = "ORDERED_BY", direction = Relationship.Direction.OUTGOING)
    private Client client;

    @Relationship(type = "CONTAINS", direction = Relationship.Direction.OUTGOING)
    private List<Contains> services;

    @Relationship(type = "EXECUTED_BY", direction = Relationship.Direction.OUTGOING)
    private Branch branch;
}