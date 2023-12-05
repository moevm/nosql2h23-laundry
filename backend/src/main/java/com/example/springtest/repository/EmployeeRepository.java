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

    @Query("MATCH (c:Employee {login: $login}) RETURN c LIMIT 1")
    Optional<Employee> findByLogin(String login);

}
