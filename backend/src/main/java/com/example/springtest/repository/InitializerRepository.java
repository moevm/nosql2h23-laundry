package com.example.springtest.repository;

import com.example.springtest.model.Initializer;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InitializerRepository extends Neo4jRepository<Initializer, UUID> {

    @Query("CREATE (i:Initializer {id: $id})")
    void setInitialized(UUID id);

    @Query("MATCH (i:Initializer) RETURN count(i) > 0")
    boolean isInitialized();

}
