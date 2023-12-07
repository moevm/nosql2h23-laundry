package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.UUID;

@Node("Initializer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Initializer {

    @Id
    private UUID id;

}
