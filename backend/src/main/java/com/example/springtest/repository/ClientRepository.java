package com.example.springtest.repository;

import com.example.springtest.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ClientRepository extends Neo4jRepository<User, Long> {
}
