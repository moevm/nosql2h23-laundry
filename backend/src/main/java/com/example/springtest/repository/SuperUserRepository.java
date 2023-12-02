package com.example.springtest.repository;


import com.example.springtest.model.SuperUser;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SuperUserRepository extends Neo4jRepository<SuperUser, Long> {
    @Query("MATCH (s:SuperUser) RETURN s")
    List<SuperUser> getAllSuperUsers();

    @Query("MATCH (s:SuperUser {fullName: $fullName}) RETURN s LIMIT 1")
    Optional<SuperUser> findByLogin(String fullName);

    @Query("MATCH (s:SuperUser {id: $id}) RETURN s LIMIT 1")
    Optional<SuperUser> getById(Long id);

    @Query("CREATE (s:SuperUser {fullName: $fullName, password: $password, email: $email, creationDate: $creationDate, editDate: $editDate, role: $role, phone: $phone, schedule: $schedule}) RETURN s")
    SuperUser addSuperUser(String fullName, String password, String email, Date creationDate, Date editDate, String role, String phone, List<String> schedule);
}
