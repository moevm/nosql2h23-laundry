package com.example.springtest.model;


import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Node("Warehouse")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Warehouse {
    @Id
    @GeneratedValue(GeneratedValue.UUIDGenerator.class)
    private String id;

    private String address;

    private List<String> schedule;

    private LocalDateTime creationDate;

    private LocalDateTime editDate;

    @Relationship(type = "STORE", direction = Relationship.Direction.OUTGOING)
    private Product product;

    @Relationship(type = "STORE", direction = Relationship.Direction.OUTGOING)
    private Employee employee;

    @Relationship(type = "MANAGE", direction = Relationship.Direction.INCOMING)
    private User user;

}
