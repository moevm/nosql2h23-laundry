package com.example.springtest.repository;

import com.example.springtest.model.Director;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DirectorRepository extends Neo4jRepository<Director, Long> {
}
