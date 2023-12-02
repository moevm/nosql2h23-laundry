package com.example.springtest.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Director")
@Getter
@Setter
public class Director extends Employee{
}