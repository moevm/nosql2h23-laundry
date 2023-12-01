package com.example.springtest.repository;

import com.example.springtest.model.Admin;
import com.example.springtest.model.Employee;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends Neo4jRepository<Employee, Long> {
}
