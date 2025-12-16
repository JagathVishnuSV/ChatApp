package com.chat.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseConfig {
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static MongoDatabase getDatabase() {
        if (database == null) {
            String uri = "mongodb://localhost:27017";
            String dbName = "chat_application";

            MongoClient mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase(dbName);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                mongoClient.close();
            }));
        }
        return database;
    }

    public static synchronized void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }
}