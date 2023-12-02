package com.example.springtest.repository;

import com.example.springtest.model.Client;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClientRepository extends Neo4jRepository<Client, Long> {
    @Query("MATCH (c:Client) RETURN c")
    List<Client> getAllClientsCustomQuery();

    @Query("CREATE (c:Client {fullName: $fullName, password: $password, email: $email, creationDate: $creationDate, editDate: $editDate}) RETURN c")
    Client addClientCustomQuery(String fullName, String password, String email, Date creationDate, Date editDate);
}
