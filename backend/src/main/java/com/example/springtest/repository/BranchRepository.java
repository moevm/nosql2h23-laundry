package com.example.springtest.repository;

import com.example.springtest.model.Branch;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BranchRepository extends Neo4jRepository<Branch, UUID> {

    @Query("MATCH (b:Branch) " +
            "MATCH (b)<-[adm:ADMINISTERS]-(admin:Employee) " +
            "MATCH (b)<-[man:MANAGE]-(director:Employee) " +
            "OPTIONAL MATCH (b)-[sup:SUPPLIED_BY]->(w:Warehouse) " +
            "WHERE b.address CONTAINS $address AND w.address CONTAINS $warehouse AND director.fullName CONTAINS $director " +
            "RETURN b, sup, w, adm, admin, man, director " +
            "SKIP $skip " +
            "LIMIT $elementsOnPage")
    List<Branch> getAllBranches(String address, String warehouse, String director, int elementsOnPage, int skip);


    @Query("MATCH (b:Branch) " +
            "MATCH (b)<-[:MANAGE]-(d:Employee) " +
            "OPTIONAL MATCH (b)-[:SUPPLIED_BY]->(w:Warehouse) " +
            "WHERE b.address CONTAINS $address AND w.address CONTAINS $warehouse AND d.fullName CONTAINS $director " +
            "RETURN count(b)")
    long getTotalCount(String address, String warehouse, String director);

    @Query("MATCH (b:Branch) " +
            "WHERE b.id in $idList " +
            "DETACH DELETE b")
    void deleteBranches(List<UUID> idList);

    @Query("MATCH (director:Employee {role: 'DIRECTOR', fullName: $directorName}) " +
            "MATCH (admin:Employee {role: 'ADMIN', fullName: $adminName}) " +
            "CREATE (director)-[manage:MANAGE]->(branch:Branch {id: $id, address: $address, schedule: $schedule, creationDate: $creationDate, editDate: $editDate})<-[administers:ADMINISTERS]-(admin) " +
            "RETURN branch, administers, admin, manage, director")
    Branch createBranch(UUID id, String address, String directorName, String adminName, List<String> schedule, LocalDateTime creationDate, LocalDateTime editDate);

    @Query("MATCH (b:Branch {id: $branchId}) " +
            "MATCH (w:Warehouse {id: $warehouseId}) " +
            "CREATE (b)-[:SUPPLIED_BY]->(w)")
    void connectBranchWithWarehouse(UUID branchId, UUID warehouseId);

    @Query("MATCH (b:Branch {address: $address}) " +
            "OPTIONAL MATCH (b)<-[ex:EXECUTED_BY]-(orders:Order) " +
            "OPTIONAL MATCH (b)-[sup:SUPPLIED_BY]->(w:Warehouse) " +
            "MATCH (b)<-[adm:ADMINISTERS]-(admin:Employee) " +
            "MATCH (b)<-[man:MANAGE]-(director:Employee) " +
            "RETURN b, collect(ex), collect(orders), sup, w, adm, admin, man, director")
    Optional<Branch> findByAddress(String address);
}
