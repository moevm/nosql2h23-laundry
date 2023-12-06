package com.example.springtest.repository;

import com.example.springtest.model.Client;
import com.example.springtest.model.Employee;
import com.example.springtest.model.types.UserRole;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends Neo4jRepository<Employee, UUID> {

    // TODO: Has double tag been written correctly?
    @Query("CREATE (e:Employee:User {id: $id, role: $role, login: $login, password: $password, fullName: $fullName, email: $email, phone: $phone, schedule: $schedule, creationDate: $creationDate, editDate: $editDate}) RETURN e")
    Employee addEmployee(UUID id, UserRole role, String login, String password, String fullName, String email, String phone, List<String> schedule, LocalDateTime creationDate, LocalDateTime editDate);

    @Query("MATCH (e:Employee {login: $login}) " +
            "OPTIONAL MATCH (e)-[ad:ADMINISTERS]->(b:Branch) " +
            "OPTIONAL MATCH (e)-[man:MANAGE]->(w:Warehouse) " +
            "OPTIONAL MATCH (e)<-[op:OPENED_BY]-(sh:Shift) " +
            "OPTIONAL MATCH (e)<-[rec:RECEIVED_BY]-(sal:Salary) " +
            "RETURN e, ad, b, man, w, collect(op), collect(sh), collect(rec), collect(sal)" +
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
}
