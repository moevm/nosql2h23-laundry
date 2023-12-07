package com.example.springtest.repository;

import com.example.springtest.model.Service;
import com.example.springtest.model.types.ServiceType;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends Neo4jRepository<Service, UUID> {

    @Query("MATCH (s:Service) " +
            "RETURN s")
    List<Service> findAllServicesNoExtraData();

    @Query("CREATE (s:Service {id: $id, type: $type, price: $price})")
    void createService(UUID id, ServiceType type, float price);

    @Query("MATCH (s:Service {type: $type}) " +
            "RETURN s")
    Optional<Service> findServiceWithType(ServiceType type);
}
