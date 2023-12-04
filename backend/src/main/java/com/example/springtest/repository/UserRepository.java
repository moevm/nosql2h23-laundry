package com.example.springtest.repository;

import com.example.springtest.model.Client;
import com.example.springtest.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, Long> {
    @Query("MATCH (u:User) RETURN u")
    List<User> getAllUsers();

    @Query("MATCH (u:User {login: $login}) RETURN u LIMIT 1")
    Optional<User> findByLogin(String login);

    @Query("MATCH (u:User {id: $id}) RETURN u LIMIT 1")
    Optional<User> findById(String login);

}
