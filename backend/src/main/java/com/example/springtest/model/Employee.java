package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("Employee")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Employee extends User {
    private String role;
    private String phone;
    private List<String> schedule;

    @Relationship(type = "ADMINISTERS", direction = Relationship.Direction.OUTGOING)
    private Branch branch;

    @Relationship(type = "MANAGE", direction = Relationship.Direction.OUTGOING)
    private Warehouse warehouse;

    @Relationship(type = "OPENED_BY", direction = Relationship.Direction.INCOMING)
    private List<Shift> shifts;

    @Relationship(type = "RECEIVED_BY", direction = Relationship.Direction.INCOMING)
    private List<Salary> salaries;
}
