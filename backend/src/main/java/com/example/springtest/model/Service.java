package com.example.springtest.model;

import com.example.springtest.model.types.ServiceType;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.UUID;

@Node("Service")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Service {

    @Id
    private UUID id;

    private ServiceType type;

    private float price;

}
