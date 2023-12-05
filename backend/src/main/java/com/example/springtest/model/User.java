package com.example.springtest.model;

import com.example.springtest.model.types.UserRole;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Node("User")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class User {

    @Id
    private UUID id;

    private UserRole role;

    private String login;

    private String password;

    private String fullName;

    private String email;

    private LocalDateTime creationDate;

    private LocalDateTime editDate;
}
