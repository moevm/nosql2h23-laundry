package com.example.springtest.repository;

import com.example.springtest.model.Product;
import com.example.springtest.model.types.ProductType;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.UUID;

@Repository
public interface ProductRepository extends Neo4jRepository<Product, UUID> {
    @Query("CREATE (p:Product {id: $id, type: $type, price: $price})")
    void createProduct(UUID id, ProductType type, float price);

    @Query("MATCH (w:Warehouse {id: $warehouseId}) " +
            "MATCH (p:Product {type: $productType}) " +
            "CREATE (w)-[:STORE {count: $count}]->(p)")
    void addNewProduct(UUID warehouseId, String productType, int count);

    @Query("MATCH (w:Warehouse {id: $warehouseId})-[store:STORE]->(p:Product {type: $productType}) " +
            "SET store.count += $count")
    void addProduct(UUID warehouseId, String productType, int count);

    @Query("MATCH (w:Warehouse {id: $warehouseId})-[store:STORE]->(p:Product {type: $productType}) " +
            "DELETE store")
    void removeWholeProduct(UUID warehouseId, String productType);

    @Query("MATCH (w:Warehouse {id: $warehouseId})-[store:STORE]->(p:Product {type: $productType}) " +
            "SET store.count -= $count")
    void removePartProduct(UUID warehouseId, String productType, int count);

    @Query("MATCH (w:Warehouse {id: $warehouseId}) " +
            "MATCH (p:Product {type: $productType}) " +
            "CREATE (w)-[:REMOVED {amount: $count, creationDate: $creationDate}]->(p)")
    void saveRemovedHistory(UUID warehouseId, String productType, int count, ZonedDateTime creationDate);
}
