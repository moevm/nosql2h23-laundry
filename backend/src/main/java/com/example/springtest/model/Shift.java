package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.util.UUID;

@Node("Shift")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Shift {

    @Id
    private UUID id;

    private LocalDate date;

    @Relationship(type = "OPENED_BY", direction = Relationship.Direction.OUTGOING)
    private Employee employee;
}
