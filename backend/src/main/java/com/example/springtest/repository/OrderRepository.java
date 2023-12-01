package com.example.springtest.repository;

import com.example.springtest.model.Order;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface OrderRepository extends Neo4jRepository<Order, Long> {
}
