package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Admin")
@Getter
@Setter
public class Admin extends Employee{
}
