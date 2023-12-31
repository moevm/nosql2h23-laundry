package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Node("Salary")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Salary {

    @Id
    private UUID id;

    private float amount;

    private LocalDate month;

    @Relationship(type = "RECEIVED_BY", direction = Relationship.Direction.OUTGOING)
    private Employee employee;
}
