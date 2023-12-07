package com.example.springtest.model.relationships;

import com.example.springtest.model.Product;
import lombok.*;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.ZonedDateTime;

@RelationshipProperties
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Removed {

    @RelationshipId
    private Long id;

    @TargetNode
    private Product product;

    private int amount;

    private ZonedDateTime creationDate;
}
