package com.example.springtest.repository;

import com.example.springtest.model.Admin;
import com.example.springtest.model.Client;
import com.example.springtest.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends Neo4jRepository<Admin, String> {
    @Query("MATCH (a:Admin) RETURN a")
    List<Admin> getAllAdmins();

    @Query("MATCH (a:Admin {fullName: $fullName}) RETURN a LIMIT 1")
    Optional<Admin> findByLogin(String fullName);

    @Query("MATCH (a:Admin {id: $id}) RETURN a LIMIT 1")
    Optional<Admin> getById(Long id);

    @Query("CREATE (a:Admin {fullName: $fullName, password: $password, email: $email, creationDate: $creationDate, editDate: $editDate, role: $role, phone: $phone, schedule: $schedule}) RETURN a")
    Admin addAdmin(String fullName, String password, String email, Date creationDate, Date editDate, String role, String phone, List<String> schedule);
}
