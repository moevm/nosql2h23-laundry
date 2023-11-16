package com.example.springtest.repository;

import com.example.springtest.model.TestModel;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TestRepository extends Neo4jRepository<TestModel, Long> {

}
