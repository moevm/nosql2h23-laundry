package com.example.springtest.repository;

import com.example.springtest.model.Shift;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ShiftRepository extends Neo4jRepository<Shift, UUID> {

    @Query("MATCH (e:Employee {id: $employeeId}) " +
            "CREATE (s:Shift {id: $id, date: $creationDate})-[:OPENED_BY]->(e)")
    void createShift(UUID id, String employeeId, LocalDate creationDate);
}
