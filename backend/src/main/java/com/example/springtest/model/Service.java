package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;
import java.util.List;

@Node("Service")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Service {
    @Id
    @GeneratedValue
    private Long id;

    private String type;

    private Float price;

    @Relationship(type = "CONTAINS", direction = Relationship.Direction.INCOMING)
    private List<Order> orders;
}
