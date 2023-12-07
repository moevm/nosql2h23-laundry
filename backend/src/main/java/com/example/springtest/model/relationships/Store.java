package com.example.springtest.model.relationships;

import com.example.springtest.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Store {

    @RelationshipId
    private Long id;

    @TargetNode
    private Product product;

    private int amount;


}
