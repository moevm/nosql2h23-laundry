package com.example.springtest.model;


import com.example.springtest.model.relationships.Store;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Node("Warehouse")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
//@ToString
public class Warehouse {
    @Id
    private UUID id;

    private String address;

    private List<String> schedule;

    private LocalDateTime creationDate;

    private LocalDateTime editDate;

    @Relationship(type = "STORE", direction = Relationship.Direction.OUTGOING)
    private List<Store> product;

    @Relationship(type = "SUPPLIED_BY", direction = Relationship.Direction.INCOMING)
    private Branch branch;

    @Relationship(type = "MANAGE", direction = Relationship.Direction.INCOMING)
    private Employee director;

}
