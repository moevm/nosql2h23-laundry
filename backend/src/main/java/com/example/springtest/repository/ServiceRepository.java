package com.example.springtest.repository;

import com.example.springtest.model.Service;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ServiceRepository extends Neo4jRepository<Service, String> {
}
