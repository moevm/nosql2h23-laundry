package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Node("Branch")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Branch {

    @Id
    private UUID id;

    private String address;

    private List<String> schedule;

    private LocalDateTime creationDate;

    private LocalDateTime editDate;

    @Relationship(type = "EXECUTED_BY", direction = Relationship.Direction.INCOMING)
    private List<Order> orders;

    @Relationship(type = "SUPPLIED_BY", direction = Relationship.Direction.OUTGOING)
    private Warehouse warehouse;

    @Relationship(type = "ADMINISTERS", direction = Relationship.Direction.INCOMING)
    private Employee admin;

    @Relationship(type = "MANAGE", direction = Relationship.Direction.INCOMING)
    private Employee director;
}
