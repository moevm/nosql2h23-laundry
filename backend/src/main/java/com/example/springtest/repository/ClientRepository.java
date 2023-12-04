package com.example.springtest.repository;

import com.example.springtest.model.Client;
import com.example.springtest.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends Neo4jRepository<Client, String> {
    @Query("MATCH (c:Client) RETURN c")
    List<Client> getAllClients();

    @Query("CREATE (c:Client {fullName: $fullName, password: $password, email: $email, creationDate: $creationDate, editDate: $editDate}) RETURN c")
    Client addClient(String fullName, String password, String email, Date creationDate, Date editDate);


    @Query("MATCH (c:Client {login: $login}) RETURN c LIMIT 1")
    Optional<Client> findByLogin(String login);

    @Query("MATCH (c:Client {id: $id}) RETURN c LIMIT 1")
    Optional<Client> getById(Long id);

}
