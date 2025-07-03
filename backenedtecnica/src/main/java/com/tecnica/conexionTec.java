package com.tecnica;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;






public class conexionTec {
    public static void main(String[] args) {
        String uri = "mongodb+srv://administrador4:18521955@cluster-tecnica.mo17m3h.mongodb.net/?retryWrites=true&w=majority&appName=cluster-tecnica";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("tecnica-db");
            MongoCollection<Document> coleccionPublis = database.getCollection("publicaciones");
            
            Document data = new Document().append("Titulo", "Prueba").append("Contenido", "Prueba").append("Imagen", "Prueba").append("Fecha","Prueba").append("Hora","Prueba").append("Tipo_de_publicacion","Prueba").append("Duracion","Prueba").append("id_publicacion","Prueba");
            InsertOneResult insertOneResult = coleccionPublis.insertOne(data);
            System.out.println("Jaja quee");
            System.out.println(insertOneResult.getInsertedId());

            FindIterable<Document> allCollection = coleccionPublis.find();
            System.out.println("\n--- find() result ---");
            allCollection.forEach(document -> System.out.println(document + "\n"));

        } catch (MongoException e) {
            System.err.println("Bardie, : " + e.getMessage());
        }
    }
}



