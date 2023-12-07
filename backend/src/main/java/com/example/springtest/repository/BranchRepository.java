package com.example.springtest.repository;

import com.example.springtest.model.Branch;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BranchRepository extends Neo4jRepository<Branch, UUID> {

    @Query("MATCH (b:Branch) " +
            "MATCH (b)<-[adm:ADMINISTERS]-(admin:Employee) " +
            "MATCH (b)<-[man:MANAGE]-(director:Employee) " +
            "WHERE b.address CONTAINS $address AND director.fullName CONTAINS $director " +
            "OPTIONAL MATCH (b)-[sup:SUPPLIED_BY]->(w:Warehouse) " +
            "RETURN b, sup, w, adm, admin, man, director " +
            "SKIP $skip " +
            "LIMIT $elementsOnPage")
    List<Branch> getAllBranches(String address, String warehouse, String director, int elementsOnPage, int skip);

    @Query("MATCH (b:Branch) " +
            "OPTIONAL MATCH (b)<-[eb:EXECUTED_BY]-(o:Order) " +
            "OPTIONAL MATCH (b)-[sb:SUPPLIED_BY]->(w:Warehouse) " +
            "RETURN b, collect(eb), collect(o), sb, w" +
            "SKIP $skip " +
            "LIMIT $elementsOnPage")
    List<Branch> getAllBranches(int elementsOnPage, int skip);

    @Query("MATCH (b:Branch) " +
            "MATCH (b)<-[adm:ADMINISTERS]-(admin:Employee) " +
            "MATCH (b)<-[man:MANAGE]-(director:Employee) " +
            "OPTIONAL MATCH (b)-[sup:SUPPLIED_BY]->(w:Warehouse) " +
            "RETURN b, sup, w, adm, admin, man, director")
    List<Branch> getAllBranches();


    @Query("MATCH (b:Branch) " +
            "MATCH (b)<-[adm:ADMINISTERS]-(admin:Employee) " +
            "MATCH (b)<-[man:MANAGE]-(director:Employee) " +
            "WHERE b.address CONTAINS $address AND director.fullName CONTAINS $director " +
            "OPTIONAL MATCH (b)-[sup:SUPPLIED_BY]->(w:Warehouse) " +
            "RETURN b, sup, w, adm, admin, man, director")
    List<Branch> getTotalCount(String address, String warehouse, String director);

    // TODO: delete warehouse also
    @Query("MATCH (b:Branch) " +
            "WHERE b.id in $idList " +
            "DETACH DELETE b")
    void deleteBranches(List<UUID> idList);

    @Query("MATCH (director:Employee {role: 'DIRECTOR', fullName: $directorName}) " +
            "MATCH (admin:Employee {role: 'ADMIN', fullName: $adminName}) " +
            "CREATE (director)-[manage:MANAGE]->(branch:Branch {id: $id, address: $address, schedule: $schedule, creationDate: $creationDate, editDate: $editDate})<-[administers:ADMINISTERS]-(admin) " +
            "RETURN branch, administers, admin, manage, director")
    Branch createBranch(UUID id, String address, String directorName, String adminName, List<String> schedule, ZonedDateTime creationDate, ZonedDateTime editDate);

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

    @Query("MATCH (b:Branch) " +
            "WHERE NOT (b)-[:SUPPLIED_BY]->() " +
            "RETURN b")
    List<Branch> findBranchesWithoutWarehouse();

}
