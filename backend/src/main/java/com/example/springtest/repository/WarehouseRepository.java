package com.example.springtest.repository;

import com.example.springtest.model.Warehouse;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface WarehouseRepository extends Neo4jRepository<Warehouse, Long> {
}
