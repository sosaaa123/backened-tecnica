package com.tecnica;

import java.util.ArrayList;
import java.util.List;

import com.tecnica.Conexion.ConexionCloud;
import com.tecnica.Conexion.ConexionMd;
import com.tecnica.Repositorio.Repositorio;
import com.tecnica.Servicio.Servicio;
import com.tecnica.Controller.Controller;
import com.tecnica.Modelos.Publicacion;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        ///Creo conexiones
        ConexionCloud cloudinary = new ConexionCloud();
        ConexionMd db = new ConexionMd();
        
        Repositorio repositorio = new Repositorio(db.getDatabase(), "publicaciones");
        Servicio servicio =  new Servicio(repositorio, cloudinary);
        Controller controlador = new Controller(servicio);
        
       
         /*
            List<String> media = new ArrayList<>();
            Publicacion pubPrueba = new Publicacion("Tercera publicacion prueba", 
                                                    "Soy la tercera publicacion prueba", 
                                                    media,
                                                    "12/2/25",
                                                    "12:00",
                                                    "Comunicado",
                                                    "Permanente");
            servicio.crearPublicacion(pubPrueba);
    

            servicio.borrarPublicacion("6865ca9f786245779f9d1347");
            servicio.borrarPublicacion("6865cd4c79322f65d52b4153");
            servicio.borrarPublicacion("6865ce638cc77b1c618dd3f1");
            servicio.borrarPublicacion("6865cf084fdf945405499344");
        */

        ///sjjsjs pasa algo rarisimo que tengo q poner los campos con la priemra en mayus si o si pq si no no se cambian(en los doc estan en minusculas ?)
        ///Cambiado en el servicio
        
        /*
         servicio.actCampo("titulo", "6887eb6ab28766f2cc1950cf", "uesoooo");
         servicio.actCampo("contenido", "6887eb6ab28766f2cc1950cf", "uesooo aaaaaaaaaaaa");
         servicio.actCampo("fecha", "6887eb6ab28766f2cc1950cf", "777/777/777");
         */


        ////pruebo esto a ver q onda pq no entiendo pq no me acepta los cors
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8081"));

        Javalin app = Javalin.create().start(port);

        ///Middleware CORS (no con javalin, probar esto)
        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*"); // o poner "http://localhost:5173" si preferís
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });

        
        app.options("/*", ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            ctx.status(200);
        });


        controlador.rutas(app);







        
    }
}