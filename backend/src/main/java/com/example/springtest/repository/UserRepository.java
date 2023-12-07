package com.example.springtest.repository;

import com.example.springtest.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends Neo4jRepository<User, UUID> {
    @Query("MATCH (u:User) RETURN u")
    List<User> getAllUsers();

    @Query("MATCH (u:User {login: $login}) RETURN u LIMIT 1")
    Optional<User> findByLogin(String login);

    @Query("MATCH (u:User {id: $id}) RETURN u LIMIT 1")
    Optional<User> findById(UUID id);
}
