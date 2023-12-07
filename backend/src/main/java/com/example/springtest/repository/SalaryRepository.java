package com.example.springtest.repository;

import com.example.springtest.model.Salary;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SalaryRepository extends Neo4jRepository<Salary, UUID> {
}
