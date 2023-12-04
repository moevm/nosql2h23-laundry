package com.example.springtest.repository;

import com.example.springtest.model.Client;
import com.example.springtest.model.Order;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends Neo4jRepository<Order, String> {

    @Query("MATCH (o:Order) RETURN o")
    List<Order> getAllOrders();

    @Query("CREATE (o:Order {status: $status, price: $price, creationDate: $creationDate, editDate: $editDate}) RETURN o")
    Order addOrder(String status, Float price, Date creationDate, Date editDate);
}
