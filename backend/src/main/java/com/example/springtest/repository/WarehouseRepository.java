package com.example.springtest.repository;

import com.example.springtest.model.Warehouse;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.UUID;

public interface WarehouseRepository extends Neo4jRepository<Warehouse, UUID> {
}
