package com.tecnica.Repositorio;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;


import com.tecnica.Modelos.Publicacion;
import static com.mongodb.client.model.Filters.*; ///Para hacer los filtros con eq y no con documents

import java.util.ArrayList;
import java.util.List;


////Ver si hago acceso de usuarios aca o en otro .java aparte
/// Tengo que ver cloudinary para java(agregar dependencias al pom.xml)
public class Repositorio {

    private MongoCollection<Document> coleccion;


    ///Construuctor, acordarse que coleccion = "publicaciones"
    public Repositorio(MongoDatabase db, String coleccion) {
        this.coleccion = db.getCollection(coleccion); //Ver si puedo hacerlo asi, si no pasar la coleccion aca ya "publicaciones".
    }

    public void crear(Publicacion publi){ ///Ver cmo hago esto

        Document data = new Document().append("Titulo", publi.getTitulo())
                                      .append("Contenido", publi.getContenido())
                                      .append("Media", publi.getMedia())
                                      .append("Fecha", publi.getFecha())
                                      .append("Hora", publi.getHora())
                                      .append("Tipo", publi.getTipo())
                                      .append("Duracion", publi.getDuracion());


         InsertOneResult insertOneResult = this.coleccion.insertOne(data);
         System.out.println(insertOneResult); ///Despues lo vuelo, quiero ver algo

    }

    public List<Publicacion> verPublicaciones() {

            List<Publicacion> publicaciones = new ArrayList<>();
            for (Document d : coleccion.find()) {

                ///El id del doc en mongo es un objeto que convierto a string
                String id = d.getObjectId("_id").toHexString();
            
                String titulo = d.getString("Titulo");
                String contenido = d.getString("Contenido");

                ///Verificar que no venga nada vacio
                List<String> media = (List<String>) d.get("Media");
                
                if (media == null) {
                    ///Creo una Lista vacia (es lo q espera la clase)
                    media = new ArrayList<>();
                }

            String fecha = d.getString("Fecha");
            String hora = d.getString("Hora");
            String tipo = d.getString("Tipo");
            String duracion = d.getString("Duracion");
            
            Publicacion pub = new Publicacion(id, titulo, contenido, media, fecha, hora, tipo, duracion); 
            publicaciones.add(pub);
        }

        return publicaciones;
    }

    public void borrarPub(String pubId){ ///26/7 cambio los string id a ObjectId (cambio en todos los metodos)

        ObjectId id = new ObjectId(pubId);
        coleccion.deleteOne(eq("_id", id));

    }


    ///En campo tengo que pasar lo que se quiere actualizar "Titulo", "Contenido, etc"
    /// Solo paracampos con valor String(todos menos Media)
    public void actualizarCampos(String campo, String pubId, String nuevoValor) {

        ///Convierto a string id a objeto Id para poder filtrearlo
        ObjectId id = new ObjectId(pubId);
        Bson filtro = Filters.eq("_id", id);

        Bson actualizacion = Updates.set(campo, nuevoValor);
    
        coleccion.updateOne(filtro, actualizacion);
    }

    ///Aca para actualizar media(borrar, agregar fotos/videos)
    /// En el frontened mme tengo que enviar una lista que sea el nuevo media(sumando un archivo, restando un archivo, toda actualizada,etc)
    public void actMedia(List<String> nMedia, String pubId) {
            
        ObjectId id = new ObjectId(pubId);

        Bson filtro = Filters.eq("_id", id);
        coleccion.updateOne(filtro, Updates.set("Media", nMedia));

    }




    public Publicacion obtPublxId(String pubId){

        ObjectId idOb = new ObjectId(pubId);
        Bson filtro = Filters.eq("_id", idOb);

        Document d = coleccion.find(filtro).first();

        String id = d.getObjectId("_id").toHexString();
            
        String titulo = d.getString("Titulo");
        String contenido = d.getString("Contenido");

        ///Verificar que no venga nada vacio
        List<String> media = (List<String>) d.get("Media");
                
        if (media == null) {media = new ArrayList<>();}

        String fecha = d.getString("Fecha");
        String hora = d.getString("Hora");
        String tipo = d.getString("Tipo");
        String duracion = d.getString("Duracion");
            
        Publicacion pub = new Publicacion(id, titulo, contenido, media, fecha, hora, tipo, duracion); 

        return pub;

    }


    


    
}
