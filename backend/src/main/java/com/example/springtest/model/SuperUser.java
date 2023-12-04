package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;

@Node("SuperUser")
@Getter
@Setter
public class SuperUser extends Employee{
}