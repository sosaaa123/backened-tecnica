package com.tecnica.Conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;

public class ConexionMd {

    private MongoClient mongoClient;
    private MongoDatabase database;

    public ConexionMd() {

        Dotenv dotenv = Dotenv.load();
        String connectionString = dotenv.get("MONGODB_URI");

        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase("tecnica-db");
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
