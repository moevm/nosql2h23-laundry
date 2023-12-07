package com.example.springtest.repository;

import com.example.springtest.model.Client;
import com.example.springtest.model.Order;
import com.example.springtest.model.types.OrderState;
import com.example.springtest.model.types.ServiceType;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends Neo4jRepository<Order, UUID> {

    @Query("MATCH (c:Client {fullName: $client}) " +
            "MATCH (b:Branch {address: $branch}) " +
            "CREATE (c)<-[ob:ORDERED_BY]-(o:Order {id: $id, price: $price, state: $state, creationDate: $creationDate, editDate: $editDate})-[eb:EXECUTED_BY]->(b) " +
            "RETURN o, ob, c, eb, b")
    Order createOrder(UUID id, float price, OrderState state, String client, String branch, ZonedDateTime creationDate, ZonedDateTime editDate);

    @Query("MATCH (o:Order {id: $id}) " +
            "MATCH (service:Service {type: $type}) " +
            "CREATE (o)-[:CONTAINS {amount: $count}]->(service)")
    void connectServiceWithOrder(UUID id, ServiceType type, int count);

    @Query("MATCH (o:Order)-[ob:ORDERED_BY]->(c:Client) " +
            "MATCH (o)-[eb:EXECUTED_BY]->(b:Branch) " +
            "MATCH (o)-[co:CONTAINS]->(service:Service) " +
            "WHERE o.creationDate >= $start AND o.creationDate <= $end AND o.state IN $possibleStates AND b.address CONTAINS $branch " +
            "RETURN o, ob, c, eb, b, collect(co), collect(service) " +
            "SKIP $skip " +
            "LIMIT $elementsOnPage")
    List<Order> findAllOrders(ZonedDateTime start, ZonedDateTime end, List<OrderState> possibleStates, String branch, int elementsOnPage, int skip);

    @Query("MATCH (o:Order)-[eb:EXECUTED_BY]->(b:Branch) " +
            "WHERE o.creationDate >= $start AND o.creationDate <= $end AND o.state IN $possibleStates AND b.address CONTAINS $branch " +
            "RETURN count(o)")
    int getTotalCount(ZonedDateTime start, ZonedDateTime end, List<OrderState> possibleStates, String branch);

    @Query("MATCH (o:Order)-[ob:ORDERED_BY]->(c:Client {id: $clientId}) " +
            "MATCH (o)-[eb:EXECUTED_BY]->(b:Branch) " +
            "MATCH (o)-[co:CONTAINS]->(service:Service) " +
            "WHERE o.creationDate >= $start AND o.creationDate <= $end AND o.state IN $possibleStates AND b.address CONTAINS $branch " +
            "RETURN o, ob, c, eb, b, collect(co), collect(service) " +
            "SKIP $skip " +
            "LIMIT $elementsOnPage")
    List<Order> findAllOrdersForClient(ZonedDateTime start, ZonedDateTime end, List<OrderState> possibleStates, String branch, int elementsOnPage, int skip, String clientId);

    @Query("MATCH (o:Order)-[eb:EXECUTED_BY]->(b:Branch) " +
            "MATCH (o)-[ob:ORDERED_BY]->(c:Client {id: $clientId}) " +
            "MATCH (o)-[co:CONTAINS]->(service:Service) " +
            "WHERE o.creationDate >= $start AND o.creationDate <= $end AND o.state IN $possibleStates AND b.address CONTAINS $branch " +
            "RETURN o, ob, c, eb, b, collect(co), collect(service)")
    List<Order> getTotalCountForClient(ZonedDateTime start, ZonedDateTime end, List<OrderState> possibleStates, String branch, String clientId);
}
