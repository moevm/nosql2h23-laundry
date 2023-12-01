package com.example.springtest.repository;

import com.example.springtest.model.Service;
import com.example.springtest.model.SuperUser;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SuperUserRepository extends Neo4jRepository<SuperUser, Long> {
}
