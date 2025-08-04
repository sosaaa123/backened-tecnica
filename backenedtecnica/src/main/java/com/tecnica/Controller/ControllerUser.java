package com.tecnica.Controller;

import com.tecnica.Servicio.UserService;

import java.util.List;
import java.util.Map;

import com.tecnica.Modelos.Usuario;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class ControllerUser {
    private UserService servicio;

    public ControllerUser(UserService servicio){
        this.servicio = servicio;

    }

    ///Hago esta funcion para validar el token y poder autorizar accesos a x rutas ("crear, borrar, ver") 
    ///Por ahora referentes a metodos relacionados a usuarios 1/8
    ///.Implementarlo luego a publicaciones para publicaciones
    ///.Tiene mas sentido y utilidad en publicaciones que aca(lo hago para que este completo)
    public Boolean autorizar(Context ctx){
        String token = ctx.header("Authorization");
        String correo = ctx.queryParam("Correo"); 

        if(servicio.autorizar(token, correo)){
            return true;
        }
        else{
            ctx.status(403).result("Invalido o no autorizado");
            return false;
        }

        

    }

    public void rutas(Javalin app){

        app.get("/usuarios", ctx -> {
            if(autorizar(ctx)){
                List<Usuario> usuarios = servicio.verUsuarios();  
                ctx.json(usuarios);
            }
            else{
                ctx.status(404).result("Credenciales no validadas");
            }
            

        });

        app.post("/usuarios", ctx -> {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            servicio.crearUsuario(usuario);
            ctx.status(HttpStatus.CREATED).result("Usuario creado");
        });

        app.delete("/usuarios/{id}", ctx ->{
            if(autorizar(ctx)){
                String id = ctx.pathParam("id");
                servicio.borrarUser(id);

                ctx.status(200).result("El usuario con id: " + id + " ha sido eliminado");
            }

            else{
                ctx.status(404).result("Credenciales no validadas");
            }

        });

        

        ///Correo
        ///Contrasena 
        ///Token
        ///En mayus
        app.post("/login", ctx ->{
            String correo = ctx.formParam("Correo");
            String contrasena = ctx.formParam("Contrasena");

            String token = servicio.validar(correo, contrasena);

            if(token == null){
                ctx.status(404).result("Credenciales no validadas");
            }
            else{
                ctx.json(Map.of("token", token));
            }
        });

        app.post("usuarios/actualizar/{id}", ctx ->{

            if(autorizar(ctx)){
                String campo = ctx.pathParam("campo");
                String id = ctx.queryParam("id");
                String nValor = ctx.queryParam("nValor");

                servicio.actualizar(campo, id, nValor);
            }

            else{
                ctx.status(404).result("Crdenciales no validadas");
            }

        });

        

    }

    


    
}
