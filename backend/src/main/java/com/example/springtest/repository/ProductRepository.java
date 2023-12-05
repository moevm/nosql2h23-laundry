package com.example.springtest.repository;

import com.example.springtest.model.Product;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.UUID;

public interface ProductRepository extends Neo4jRepository<Product, UUID> {
}
