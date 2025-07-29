package com.tecnica.Modelos;

import java.util.List;

public class Publicacion{

    private String id;

    private String titulo;
    private String contenido;//Texto del archivo

    ///27/7 tuve que cambiar media a  objeto lista pq tenia problemas para pasarlo a mongoDb(no aceptaba arrays)
    private List<String> media;
    private String fecha;
    private String hora;

    private String tipo; //Aviso, noticia, general
    private String duracion; //Permanente, mensual, anual, semanal (???


    public Publicacion(){} ///Constructor vacio, de prueba !!!

    public Publicacion(String id, String titulo, String contenido, List<String> media, String fecha, String hora, String tipo, String duracion ){

            this.id = id;
            this.titulo = titulo;
            this.contenido  = contenido;
            this.media = media; ///Ver si hago ponerle false por defecto.
            this.fecha = fecha;
            this.hora = hora;
            this.tipo = tipo;
            this.duracion = duracion;
    }

    ///Borrar despues este constructor pq no me va servir(solo para pruebas (const sin id)
    public Publicacion( String titulo, String contenido, List<String> media, String fecha, String hora, String tipo, String duracion ){

            this.titulo = titulo;
            this.contenido  = contenido;
            this.media = media; ///Ver si hago ponerle false por defecto.
            this.fecha = fecha;
            this.hora = hora;
            this.tipo = tipo;
            this.duracion = duracion;
    }


    ///No puedo creer que le tenga que hacer getters y setters a todo
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }


    public String getContenido() {
        return contenido;
    }
    public void setContenido(String contenido){
        this.contenido = contenido;
    }


    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha){
        this.fecha = fecha;
    }


    //////Acordarse que media es list para las urls
    public List<String> getMedia(){
        return media;
    }
    public void setMedia(List<String> media){
        this.media = media;
    }
    

    public String getHora(){
        return hora;
    }
    public void setHora(String hora){
        this.hora = hora;
    }


    public String getId(){
        return id;
    }
    


    public String getTipo(){
        return tipo;
    }
    public void setTipo(String tipo){
        this.id = tipo;
    }


    
    public String getDuracion(){
        return duracion;
    }
    public void setDuracion(String duracion){
        this.duracion = duracion;
    }

}