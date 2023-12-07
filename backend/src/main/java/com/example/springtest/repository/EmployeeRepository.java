package com.example.springtest.repository;

import com.example.springtest.model.Employee;
import com.example.springtest.model.types.UserRole;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends Neo4jRepository<Employee, UUID> {

    // TODO: Has double tag been written correctly?
    @Query("CREATE (e:Employee:User {id: $id, role: $role, login: $login, password: $password, fullName: $fullName, email: $email, phone: $phone, schedule: $schedule, creationDate: $creationDate, editDate: $editDate}) RETURN e")
    Employee createEmployee(UUID id, UserRole role, String login, String password, String fullName, String email, String phone, List<String> schedule, ZonedDateTime creationDate, ZonedDateTime editDate);

    @Query("MATCH (e:Employee {login: $login}) " +
            "OPTIONAL MATCH (e)-[ad:ADMINISTERS]->(b:Branch) " +
            "OPTIONAL MATCH (e)-[man1:MANAGE]->(b1:Branch) " +
            "OPTIONAL MATCH (e)-[man2:MANAGE]->(w:Warehouse) " +
            "OPTIONAL MATCH (e)<-[op:OPENED_BY]-(sh:Shift) " +
            "OPTIONAL MATCH (e)<-[rec:RECEIVED_BY]-(sal:Salary) " +
            "RETURN e, ad, b1, man1, b, man2, w, collect(op), collect(sh), collect(rec), collect(sal) " +
            "LIMIT 1")
    Optional<Employee> findByLogin(String login);


    @Query("MATCH (e:Employee {role: 'DIRECTOR'}) " +
            "WHERE NOT (e)-[:MANAGE]->() " +
            "RETURN e")
    List<Employee> findDirectorWithoutBranch();

    @Query("MATCH (e:Employee {role: 'ADMIN'}) " +
            "WHERE NOT (e)-[:ADMINISTERS]->() " +
            "RETURN e")
    List<Employee> findAdminWithoutBranch();

    @Query("MATCH (e:Employee) " +
            "WHERE e.fullName CONTAINS $name AND e.phone CONTAINS $phone AND e.role IN $possibleRoles " +
            "OPTIONAL MATCH (e)-[ad:ADMINISTERS]->(b:Branch) " +
            "OPTIONAL MATCH (e)-[man1:MANAGE]->(b1:Branch) " +
            "OPTIONAL MATCH (e)-[man2:MANAGE]->(w:Warehouse) " +
            "OPTIONAL MATCH (e)<-[op:OPENED_BY]-(sh:Shift) " +
            "OPTIONAL MATCH (e)<-[rec:RECEIVED_BY]-(sal:Salary) " +
            "RETURN e, ad, b, man1, b1, man2, w, collect(op), collect(sh), collect(rec), collect(sal) " +
            "SKIP $skip " +
            "LIMIT $elementsOnPage")
    List<Employee> getAllEmployees(String name, String phone, List<UserRole> possibleRoles, int elementsOnPage, int skip);

    @Query("MATCH (e:Employee) " +
            "WHERE e.fullName CONTAINS $name AND e.phone CONTAINS $phone AND e.role IN $possibleRoles " +
            "RETURN count(e)")
    int getTotalCount(String name, String phone, List<UserRole> possibleRoles);

    // TODO: cascade?
    @Query("MATCH (e:Employee) " +
            "WHERE e.id IN $idList " +
            "DETACH DELETE e")
    void deleteEmployees(List<UUID> idList);
}
