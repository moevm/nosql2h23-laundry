package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;
import java.util.UUID;

@Node("Product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {

    @Id
    private UUID id;

    private String type;

    private Float price;
}
