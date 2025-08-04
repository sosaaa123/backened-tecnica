package com.tecnica;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.http.HttpTokens.Token;

import com.tecnica.Conexion.ConexionCloud;
import com.tecnica.Conexion.ConexionMd;
import com.tecnica.Repositorio.*;
import com.tecnica.Servicio.*;
import com.tecnica.Controller.*;
import com.tecnica.Modelos.*;
import com.tecnica.TokenManager.TokenManager;

import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;

////3/8 Cosas q me faltan:

///. Terminar el controller de usuarios (definiendo rutas y headers del token)
///. Revisar el controller de usuario y borrar los archivos temporales
///. Repasar los controllers, implementar en todos ctx.status().result() dependiendo el caso
///. Capaz despues borro el metodo actualizar contraseña, agregarle mas metodos de seguridad o hacerlo un metodo aparte
public class Main {
    public static void main(String[] args) {
        ///Creo conexiones
        ConexionCloud cloudinary = new ConexionCloud();
        ConexionMd db = new ConexionMd();
        
        Repositorio repositorio = new Repositorio(db.getDatabase(), "publicaciones");
        Servicio servicio =  new Servicio(repositorio, cloudinary);
        Controller controlador = new Controller(servicio);

        UserRep userRep = new UserRep(db.getDatabase(), "usuarios");
        ///No se si es el mejor lugar para poner la secret_key(preguntar a algun profe, sino la paso en el constructor en su clase)
        Dotenv dotenv = Dotenv.load();
        String tokenKey  = dotenv.get("SECRET_KEY");

        TokenManager tokenManager = new TokenManager(tokenKey);
        UserService userService = new UserService(userRep, tokenManager);
        ControllerUser controllerUser = new ControllerUser(userService);

        
        ///Usuario usuarioPrueba = new Usuario("Lautaro", "Sosa", "lautasosita097@gmail.com", "panconqueso12");
        ///userRep.crear(usuarioPrueba);
        ///userRep.actualizar("Apellido", "68902742078425245974e5e4", "Duarte");
        //userService.crearUsuario(usuarioPrueba);
        ///userService.borrarUser("68902742078425245974e5e4");

        
       
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
        ///int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8081"));

       

        Javalin app = Javalin.create(config -> {
                config.bundledPlugins.enableCors(cors -> {
                    cors.addRule(it -> {
                    it.anyHost();
                });
            });
        }).start(8081);

        

        controlador.rutas(app);
        controllerUser.rutas(app);

    



        
    }
}