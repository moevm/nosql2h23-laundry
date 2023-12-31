package com.example.springtest.model.relationships;

import com.example.springtest.model.Service;
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
public class Contains {
    @RelationshipId
    private Long id;

    @TargetNode
    private Service service;

    private int amount;
}
