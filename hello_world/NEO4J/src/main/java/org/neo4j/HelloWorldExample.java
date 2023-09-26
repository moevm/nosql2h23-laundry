package org.neo4j;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import static org.neo4j.driver.Values.parameters;

public class HelloWorldExample implements AutoCloseable {
    private final Driver driver;

    public HelloWorldExample(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws RuntimeException {
        driver.close();
    }

    public void printGreeting(final String message) {
        try (Session session = driver.session()) {
             Object greeting = session.executeWrite(tx -> {
                Query query = new Query("CREATE (a:Greeting) SET a.message = $message RETURN a.message + ', from node ' + id(a)", parameters("message", message));
                Result result = tx.run(query);
                return result.single().get(0).asString();
            });
            System.out.println(greeting);
        }
    }

    public void readGreeting() {
        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
                Query query = new Query("MATCH (a:Greeting) RETURN a.message");
                Result result = tx.run(query);
                while (result.hasNext()) {
                    Record record = result.next();
                    String message = record.get(0).asString();
                    System.out.println("Read message from database: " + message);
                }
                return null;
            });
        }
    }

    public void deleteGreetingNodes() {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                Query query = new Query("MATCH (a:Greeting) DELETE a");
                tx.run(query);
                return null;
            });
        }
    }


    public static void main(String... args) {
        try (HelloWorldExample greeter = new HelloWorldExample("bolt://localhost:7687", "neo4j", "mynewpassword")) {
            greeter.printGreeting("Hello, world!");
            greeter.printGreeting("Hi, world!");
            greeter.printGreeting("Привет, мир!");
            System.out.println("====================");
            greeter.readGreeting();
            System.out.println("================");
            greeter.deleteGreetingNodes();
            System.out.println("Узлы успешно удалены!");
            System.out.println("===============");
            greeter.readGreeting();
        }
    }
}