package com.example.springtest.repository;

import com.example.springtest.model.Warehouse;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseRepository extends Neo4jRepository<Warehouse, UUID> {

    // Mapping everything here is not necessary
    @Query("MATCH (w:Warehouse) " +
            "WHERE NOT (w)<-[:SUPPLIED_BY]-() " +
            "RETURN w")
    List<Warehouse> findWarehousesWithoutBranch();

    @Query("MATCH (w:Warehouse {address: $warehouseAddress}) " +
            "OPTIONAL MATCH (w)-[st:STORE]->(pr:Product) " +
            "OPTIONAL MATCH (w)<-[sup:SUPPLIED_BY]-(b:Branch) " +
            "OPTIONAL MATCH (w)<-[man:MANAGE]-(dir:Employee) " +
            "RETURN w, collect(st), collect(pr), sup, b, man, dir " +
            "LIMIT 1")
    Optional<Warehouse> findByAddress(String warehouseAddress);
}
