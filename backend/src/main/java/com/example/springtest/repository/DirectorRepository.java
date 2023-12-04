package com.example.springtest.repository;

import com.example.springtest.model.Admin;
import com.example.springtest.model.Director;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DirectorRepository extends Neo4jRepository<Director, String> {
    @Query("MATCH (d:Director) RETURN d")
    List<Director> getAllDirectors();

    @Query("MATCH (d:Director {fullName: $fullName}) RETURN d LIMIT 1")
    Optional<Director> findByLogin(String fullName);

    @Query("MATCH (d:Director {id: $id}) RETURN d LIMIT 1")
    Optional<Director> getById(Long id);

    @Query("CREATE (d:Director {fullName: $fullName, password: $password, email: $email, creationDate: $creationDate, editDate: $editDate, role: $role, phone: $phone, schedule: $schedule}) RETURN d")
    Director addDirector(String fullName, String password, String email, LocalDateTime creationDate, LocalDateTime editDate, String role, String phone, List<String> schedule);
}
