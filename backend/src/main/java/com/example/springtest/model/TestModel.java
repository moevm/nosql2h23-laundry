package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Node
public class TestModel {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

}
