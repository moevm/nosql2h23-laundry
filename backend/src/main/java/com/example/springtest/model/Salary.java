package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;

@Node("Salary")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Salary {

    @Id
    @GeneratedValue(GeneratedValue.UUIDGenerator.class)
    private String id;

    private Float amount;

    private Date month;

    @Relationship(type = "RECEIVED_By", direction = Relationship.Direction.OUTGOING)
    private Employee employee;
}
