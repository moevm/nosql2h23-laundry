package com.example.springtest.repository;

import com.example.springtest.model.Shift;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.UUID;

public interface ShiftRepository extends Neo4jRepository<Shift, UUID> {
}
