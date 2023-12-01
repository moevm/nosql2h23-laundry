package com.example.springtest.repository;

import com.example.springtest.model.Admin;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends Neo4jRepository<Admin, Long> {
}
