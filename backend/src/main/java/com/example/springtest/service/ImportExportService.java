package com.example.springtest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImportExportService {

    private final Neo4jClient client;

    public String exportData() {
        Map<String, Object> result = client
                .query("CALL apoc.export.json.all(null, {useTypes:true, stream:true}) " +
                        "YIELD data " +
                        "RETURN data ")
                .fetch()
                .one()
                .orElse(Map.of());

        return (String) result.get("data");
    }

    public void importData() {

        List<String> models = List.of(
                "Branch", "Client", "Employee", "Initializer",
                "Order", "Product", "Salary", "Service", "Shift",
                "User", "Warehouse"
        );

        for (String modelName: models) {
            try {
                client.query(String.format("""
                    CREATE CONSTRAINT FOR (n:%s) REQUIRE n.neo4jImportId IS UNIQUE;
                    """, modelName))
                        .run();
            } catch (Exception ignored) {}
        }


        client.query("""
                        MATCH (n) DETACH DELETE (n)
                        """)
                .run();
        client.query("""
                        CALL apoc.import.json("import.json", {
                            nodePropertyMappings: {
                                Branch: {creationDate: 'Zoneddatetime', editDate: 'Zoneddatetime'},
                                Order: {creationDate: 'Zoneddatetime', editDate: 'Zoneddatetime'},
                                Shift: {date: 'Localdate'},
                                User: {creationDate: 'Zoneddatetime', editDate: 'Zoneddatetime'},
                                Warehouse: {creationDate: 'Zoneddatetime', editDate: 'Zoneddatetime'}
                            },
                            relPropertyMappings: {
                                REMOVED: {creationDate: 'Zoneddatetime'}
                            }
                        })""")
                .run();
    }
}
