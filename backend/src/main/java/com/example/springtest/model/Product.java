package com.example.springtest.model;

import com.example.springtest.model.types.ProductType;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

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

    private ProductType type;

    private float price;
}
