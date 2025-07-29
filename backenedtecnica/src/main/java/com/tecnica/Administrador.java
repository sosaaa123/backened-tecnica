package com.tecnica;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

///Estudiar arquitectura de capas.

public class Administrador {

    public InsertOneResult crearPublicacion(String Titulo, String Contenido, String Imagen, String Fecha, String Hora, String Tipo_de_pulicacion, String Duracion ,int id_publicacion){

        String uri = "mongodb+srv://administrador4:18521955@cluster-tecnica.mo17m3h.mongodb.net/?retryWrites=true&w=majority&appName=cluster-tecnica";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("tecnica-db");
        MongoCollection<Document> publicaciones = database.getCollection("publicaciones");
        
        
        Document data = new Document().append("Titulo", Titulo).append("Contenido", Contenido).append("Imagen", Imagen).append("Fecha", Fecha).append("Hora", Hora).append("Tipo_de_publicacion", Tipo_de_pulicacion).append("Duracion",Duracion).append("id_publicacion", id_publicacion);
        InsertOneResult insertOneResult = publicaciones.insertOne(data);
        
        return insertOneResult;
        
    }


    public FindIterable<Document> verPublicaciones(){

        String uri = "mongodb+srv://administrador4:18521955@cluster-tecnica.mo17m3h.mongodb.net/?retryWrites=true&w=majority&appName=cluster-tecnica";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("tecnica-db");
        MongoCollection<Document> publicaciones = database.getCollection("publicaciones");

        FindIterable<Document> allCollection = publicaciones.find();
        
        return allCollection;

    }

    public FindIterable<Document> actualizarPublicacionesTitulo(int id_publicacion, String nTitulo){

        String uri = "mongodb+srv://administrador4:18521955@cluster-tecnica.mo17m3h.mongodb.net/?retryWrites=true&w=majority&appName=cluster-tecnica";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("tecnica-db");
        MongoCollection<Document> publicaciones = database.getCollection("publicaciones");

        Bson filter = Filters.eq("id", id_publicacion);
        FindIterable<Document> publicacionBuscada = publicaciones.find(filter);
        
        Bson newTitulo = Updates.set("Titulo", nTitulo);
        UpdateResult updateOne = publicaciones.updateOne(filter, newTitulo);
        updateOne.getMatchedCount();



        return publicacionBuscada;
    }

    


}
