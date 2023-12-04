package com.example.springtest.repository;

import com.example.springtest.model.Branch;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface BranchRepository extends Neo4jRepository<Branch, String> {
}
