package com.example.springtest.repository;

import com.example.springtest.model.Branch;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.UUID;

public interface BranchRepository extends Neo4jRepository<Branch, UUID> {

    @Query("MATCH (b:Branch) " +
            "OPTIONAL MATCH (b)-[:SUPPLIED_BY]->(w:Warehouse) " +
            "OPTIONAL MATCH (b)<-[:MANAGE]-(d:Employee) " +
            "WHERE b.address =~ $address AND w.address =~ $warehouse AND d.fullName =~ $director " +
            "RETURN b " +
            "SKIP $skip " +
            "LIMIT $elementsOnPage")
    List<Branch> getAllBranches(String address, String warehouse, String director, int elementsOnPage, int skip);

    // TODO: unknown behavior if optional match will return null!
    @Query("MATCH (b:Branch) " +
            "OPTIONAL MATCH (b)-[:SUPPLIED_BY]->(w:Warehouse) " +
            "OPTIONAL MATCH (b)<-[:MANAGE]-(d:Employee) " +
            "WHERE b.address =~ $address AND w.address =~ $warehouse AND d.fullName =~ $director " +
            "RETURN count(b)")
    long getTotalCount(String address, String warehouse, String director);

    @Query("MATCH (b:Branch) " +
            "WHERE b.id in $idList " +
            "DETACH DELETE b")
    void deleteBranches(List<UUID> idList);

}
