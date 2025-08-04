package com.tecnica.Repositorio;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;


import com.tecnica.Modelos.Usuario;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;


///Para el repositorio usuarios voy a hacer un crud igual que en el repo de publicaciones. 
/// 
///No se cuantas personas serian los "administradores" de la pagina, por cuestiones de responsabilidad y seguridad
///considero que los usuarios deberian ser creados a partir de correos reales de los correspondientes "admnistradores"
///por eso realizar tdos los metodos para crear, eliminar y actualizar usuarios. 
public class UserRep{

    private MongoCollection<Document> coleccion;
    ///Coleccion "usuarios"
    public UserRep(MongoDatabase db, String coleccion){
        this.coleccion = db.getCollection(coleccion);
    }

    public void crear(Usuario usuario){
        Document data = new Document().append("Nombre", usuario.getNombre())
                                      .append("Apellido", usuario.getApellido())
                                      .append("Correo", usuario.getCorreo())
                                      .append("Contrasena", usuario.getContra());

        InsertOneResult insertOneResult = this.coleccion.insertOne(data);
        System.err.println(insertOneResult);
    }

    public void borrarUser(String userId){ ///26/7 cambio los string id a ObjectId (cambio en todos los metodos)

        ObjectId id = new ObjectId(userId);
        coleccion.deleteOne(eq("_id", id));

    }

    public List<Usuario> verUsuarios(){

        List<Usuario> usuarios = new ArrayList<>();
        for(Document d : coleccion.find()){
            String id = d.getObjectId("_id").toHexString();
            String nombre = d.getString("Nombre");
            String apellido = d.getString("Apellido");
            String correo = d.getString("Correo");
            String contrasena = d.getString("Contrasena");

            Usuario usuario = new Usuario(id, nombre, apellido ,correo, contrasena);
            usuarios.add(usuario);
        }

        return usuarios;
    }

    public void actualizar(String campo, String userId, String nValor) {

        ObjectId id = new ObjectId(userId);
        Bson filtro = Filters.eq("_id", id);
        Bson actualizacion = Updates.set(campo, nValor);
    
        coleccion.updateOne(filtro, actualizacion);
    }

    public Usuario obtUserxId(String id){

        ObjectId id_user = new ObjectId(id);
        Bson filtro = Filters.eq("_id", id_user);

        Document d = coleccion.find(filtro).first();
        

        String nombre = d.getString("Nombre");
        String apellido = d.getString("Apellido");
        String correo = d.getString("Correo");
        String contrasena = d.getString("Contrasena");

        Usuario usuario = new Usuario(id, nombre, apellido,correo, contrasena);

        return usuario;
    }

    ///Este es el metodo que voy a usar para validar
    public Usuario obtUserxCorr(String correo){
        Bson filtro = Filters.eq("Correo", correo);

        Document d = coleccion.find(filtro).first();
        ///Compruebo que el correo exista(ver UserService validar())
        if(d == null){
            return null;
        }
        String id = d.getObjectId("_id").toHexString();

        String nombre = d.getString("Nombre");
        String apellido = d.getString("Apellido");
        String contrasena = d.getString("Contrasena");

        Usuario usuario = new Usuario(id, nombre,apellido,correo, contrasena);

        return usuario;

    }


    



}
