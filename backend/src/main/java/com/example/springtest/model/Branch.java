package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;
import java.util.List;

@Node("Branch")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Branch {

    @Id
    @GeneratedValue(GeneratedValue.UUIDGenerator.class)
    private String id;

    private String address;

    private List<String> schedule;

    private Date creationDate;

    private Date editDate;

    @Relationship(type = "EXECUTED_BY", direction = Relationship.Direction.INCOMING)
    private List<Order> orders;

    @Relationship(type = "SUPPLIED_BY", direction = Relationship.Direction.OUTGOING)
    private Warehouse warehouse;

    @Relationship(type = "ADMINISTERS", direction = Relationship.Direction.INCOMING)
    private Employee employee;
}
