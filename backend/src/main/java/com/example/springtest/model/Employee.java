package com.example.springtest.model;

import com.example.springtest.model.types.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Node("Employee")
@Getter
@Setter
//@ToString(callSuper = true)
public class Employee extends User {

    private String phone;

    private List<String> schedule;

    @Relationship(type = "ADMINISTERS", direction = Relationship.Direction.OUTGOING)
    private Branch branchAdmin;

    @Relationship(type = "MANAGE", direction = Relationship.Direction.OUTGOING)
    private Branch branchDirector;

    @Relationship(type = "MANAGE", direction = Relationship.Direction.OUTGOING)
    private Warehouse warehouse;

    @Relationship(type = "OPENED_BY", direction = Relationship.Direction.INCOMING)
    private List<Shift> shifts;

    @Relationship(type = "RECEIVED_BY", direction = Relationship.Direction.INCOMING)
    private List<Salary> salaries;

    @Builder(builderMethodName = "employeeBuilder")
    public Employee(UUID id, UserRole role, String login, String password, String fullName, String email, ZonedDateTime creationDate, ZonedDateTime editDate, String phone, List<String> schedule) {
        super(id, role, login, password, fullName, email, creationDate, editDate);
        this.phone = phone;
        this.schedule = schedule;
    }
}
