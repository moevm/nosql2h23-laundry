package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDateTime;
import java.util.Date;


@Node("User")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(GeneratedValue.UUIDGenerator.class)
    private String id;

    private String password;

    private String fullName;

    private String email;

    private LocalDateTime creationDate;

    private LocalDateTime editDate;
}
