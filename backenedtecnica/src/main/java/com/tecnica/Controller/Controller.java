package com.tecnica.Controller;

import io.javalin.Javalin;
import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.List;

import com.tecnica.Servicio.Servicio;
import com.tecnica.Modelos.Publicacion;
///Importo jackson object mapper
import com.fasterxml.jackson.databind.ObjectMapper;

///Tengo que ver manejos de 

public class Controller{
    private Servicio servicio;

    public Controller(Servicio servicio) {
        this.servicio = servicio;
    }


    ///Aca le paso la app cuando la creo(creada en el main)
    public void rutas(Javalin app) {

        app.get("/", ctx ->{
            ctx.json("Corriendose");
        });

        app.get("/publicaciones", ctx -> {

            List<Publicacion> publicaciones = servicio.publicaciones();
            ///Devuelvo las publicaciones como json
            ctx.json(publicaciones);

        });


        ////Nueva ruta 18/8 para poder ver cada publicacion a partir del ver-mas
        app.get("publicaciones/{id}", ctx ->{

            String id = ctx.pathParam(("id"));
            ctx.json(servicio.obtPublxId(id));

        });


        ///Importante: definir bien en el front como va a ser el form-data para recibir los archivos
        ///Primero el Json de la publicacion y luego los archivos bajo el nombre de "Media" asi les puedo hacer un map aca(
        /// ver como hacer envio de archivos).
        
        ///Voy a usar Jackson para transformar el json del form-data en Publicacion
        ///Tengo que ver como crear archivos temporales para recibirlos y poder pasarselos a cloudinary
        ///Los archivos a subir yo los recibo como de clase UploadedFiles 
        app.post("/publicaciones", ctx -> {

            ObjectMapper mapper = new ObjectMapper();
            ///Tengo que asegurarme que el form-data este como "publicacion"(ver desde el frontend)
            String json = ctx.formParam("publicacion");
            
            ///Convierto el json a pub usando jackson queeee
            Publicacion pub = mapper.readValue(json, Publicacion.class);

            ///Asegurarme que el form data tenga "media"
            /// Y ahora que verga hago
            
            
            List<File> media = new ArrayList<>();

            ///for each para los UploadedFiles
            for(UploadedFile arch: ctx.uploadedFiles("media")){
                String nombre = arch.filename();
                ///Con esto saco la extension para poder pasarsela al createTempFile
                String extension = nombre.substring(nombre.lastIndexOf('.'));
                ///Creo el archivo
                File temp = File.createTempFile("ArchivoTecnica", extension);
                ///Le escribo los datos
                arch.content().transferTo(new FileOutputStream(temp));
                ///Ver como borrarlos
                ///FileOutputStream x = new FileOutputStream(temp);
                
                media.add(temp);

            }
            
            


            servicio.crearPub(pub, media);
            ctx.status(201).result("Creada");

        });

        app.delete("/publicaciones/{id}", ctx -> {
            ///Le paso la id como parametro para borrarla(mas facil que hacer un post)
            String id = ctx.pathParam("id");
            servicio.borrarPub(id);

            ctx.status(200).result("Se ha eliminado correctamente la publicacion");

        });


        app.put("/publicaciones/{id}", ctx -> {

            String id = ctx.pathParam("id");
            String campo = ctx.queryParam("campo");
            String nuevoValor = ctx.queryParam("valor");

            servicio.actCampo(campo, id, nuevoValor);
            ctx.status(200).result("esooooooo");

        });

        
    }
}

