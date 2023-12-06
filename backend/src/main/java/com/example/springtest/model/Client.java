package com.example.springtest.model;

import com.example.springtest.model.types.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Node("Client")
@Getter
@Setter
//@ToString(callSuper = true)
public class Client extends User {

    @Relationship(type = "ORDERED_BY", direction = Relationship.Direction.INCOMING)
    private List<Order> orders;

    @Builder(builderMethodName = "clientBuilder")
    public Client(UUID id, String login, String password, String fullName, String email, LocalDateTime creationDate, LocalDateTime editDate, List<Order> orders) {
        super(id, UserRole.CLIENT, login, password, fullName, email, creationDate, editDate);
        this.orders = orders;
    }
}
