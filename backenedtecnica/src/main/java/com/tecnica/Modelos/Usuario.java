package com.tecnica.Modelos;

public class Usuario {
    private String id;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;

    public Usuario(){}

    public Usuario( String nombre, String apellido ,String correo, String contrasena){
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
    }


    public Usuario(String id, String nombre, String apellido ,String correo, String contrasena){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getId(){
        return this.id;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getApellido(){
        return this.apellido;
    }

    public String getContra(){
        return this.contrasena;
    }

    public String getCorreo(){
        return this.correo;
    }

    public void setNombre(String nNombre){

        this.nombre = nNombre;

    }

    public void setApellido(String nApellido){
        this.apellido = nApellido;
    }

    public void setContra(String nContra){

        this.contrasena = nContra;

    }

    public void setCorreo(String nCorreo){

        this.correo = nCorreo;

    }


    
}
