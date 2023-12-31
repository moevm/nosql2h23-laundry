package com.example.springtest.repository;

import com.example.springtest.model.Client;
import com.example.springtest.model.types.UserRole;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends Neo4jRepository<Client, UUID> {

    @Query("MATCH (c:Client) " +
            "WHERE c.fullName CONTAINS $name AND c.email CONTAINS $email " +
            "RETURN c " +
            "SKIP $skip " +
            "LIMIT $elementsOnPage")
    List<Client> getAllClients(String name, String email, int elementsOnPage, int skip);

    @Query("MATCH (c:Client) " +
            "RETURN c")
    List<Client> getAllClients();

    @Query("CREATE (c:Client:User {id: $id, role: $role, login: $login, password: $password, fullName: $fullName, email: $email, creationDate: $creationDate, editDate: $editDate}) RETURN c")
    Client addClient(UUID id, UserRole role, String login, String password, String fullName, String email, ZonedDateTime creationDate, ZonedDateTime editDate);


    @Query("MATCH (c:Client {login: $login}) " +
            "OPTIONAL MATCH (c)<-[ord:ORDERED_BY]-(orders:Order) " +
            "RETURN c, collect(ord), collect(orders) " +
            "LIMIT 1")
    Optional<Client> findByLogin(String login);

    @Query("MATCH (c:Client {id: $id}) " +
            "RETURN c " +
            "LIMIT 1")
    Optional<Client> findClientById(UUID id);

    @Query("MATCH (c:Client) " +
            "WHERE c.fullName CONTAINS $name AND c.email CONTAINS $email " +
            "RETURN count(c)")
    int getTotalCount(String name, String email);

    // TODO: cascade?
    @Query("MATCH (c:Client) " +
            "WHERE c.id in $idList " +
            "DETACH DELETE c")
    void deleteClients(List<UUID> idList);
}
