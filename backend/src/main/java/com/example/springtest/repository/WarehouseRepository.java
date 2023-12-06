package com.example.springtest.repository;

import com.example.springtest.model.Warehouse;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.time.LocalDateTime;
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
            "MATCH (w)<-[sup:SUPPLIED_BY]-(b:Branch) " +
            "OPTIONAL MATCH (w)-[st:STORE]->(pr:Product) " +
            "OPTIONAL MATCH (w)<-[man:MANAGE]-(dir:Employee) " +
            "RETURN w, collect(st), collect(pr), sup, b, man, dir " +
            "LIMIT 1")
    Optional<Warehouse> findByAddress(String warehouseAddress);

    @Query("MATCH (w:Warehouse) " +
            "MATCH (w)<-[sup:SUPPLIED_BY]-(b:Branch) " +
            "WHERE w.address CONTAINS $address AND b.address CONTAINS $branch " +
            "OPTIONAL MATCH (w)-[st:STORE]->(pr:Product) " +
            "OPTIONAL MATCH (w)<-[man:MANAGE]-(dir:Employee) " +
            "RETURN w, collect(st), collect(pr), sup, b, man, dir " +
            "SKIP $skip " +
            "LIMIT $elementsOnPage")
    List<Warehouse> getAllWarehouses(String address, String branch, int elementsOnPage, int skip);

    @Query("MATCH (w:Warehouse)<-[sup:SUPPLIED_BY]-(b:Branch) " +
            "WHERE w.address CONTAINS $address AND b.address CONTAINS $branch " +
            "RETURN count(w)")
    int getTotalCount(String address, String branch);


    @Query("MATCH (w:Warehouse) " +
            "WHERE w.id in $idList " +
            "DETACH DELETE w")
    void deleteWarehouses(List<UUID> idList);

    @Query("MATCH (branch:Branch {address: $branch})<-[:MANAGE]-(director:Employee) " +
            "CREATE (director)-[:MANAGE]->(warehouse:Warehouse {id: $id, address: $address, schedule: $schedule, creationDate: $creationDate, editDate: $editDate})<-[:SUPPLIED_BY]-(branch)")
    void createWarehouse(UUID id, String address, String branch, List<String> schedule, LocalDateTime creationDate, LocalDateTime editDate);
}
