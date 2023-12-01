package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;

@Node("Shift")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Salary {
    @Id
    @GeneratedValue
    private Long id;
    private Float amount;
    private Date month;

    @Relationship(type = "RECEIVED_By", direction = Relationship.Direction.OUTGOING)
    private Employee employee;
}
