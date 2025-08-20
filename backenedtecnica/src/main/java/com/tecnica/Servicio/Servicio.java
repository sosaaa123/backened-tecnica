///Voy a hacer una clase servicio solo porque quiero respetar toda la arquitectura en capas
///Por ahora voy a hacerlo asi nomas para poder ya hacer y probar con controller
///Urgente luego pensar en que validaciones hacer(sobre todo en envios vacios/incorrectos/incompletos
/// pero que igual se pueden solucionar desde el frontened(no sobrecargar el backened de consultas))

///Manejar validaciones con throw(ver como funciona throw)

package com.tecnica.Servicio;

import com.tecnica.Modelos.Publicacion;
import com.tecnica.Repositorio.Repositorio;
import com.tecnica.Conexion.ConexionCloud;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Servicio {

    private Repositorio repo;
    private ConexionCloud cloud;

    public Servicio(Repositorio repo, ConexionCloud cloud) {
        this.repo = repo;
        this.cloud = cloud;
    }

    public List<Publicacion> publicaciones() {return repo.verPublicaciones();}

    ///Hacer validaciones para no subir publicaciones vacias con un if()
    ///Lo mesmo en el front
    public void crearPub(Publicacion pub, List<File> media) {

        List<String> nmedia = cloud.subMedia(media);
        pub.setMedia(nmedia);
        repo.crear(pub);
    }


    public Publicacion obtPublxId(String pubId){
        return repo.obtPublxId(pubId);
    }
    

    public void borrarPub(String id) {repo.borrarPub(id);}

    
    public void actCampo(String campo, String publiId, String valor) {
        ///Importante: paso el campo con la primera en mayuscula, pq si ActCampo no funciona(igual pedir que el fronetened envie los campos en
        /// mayus)
        campo = campo.toUpperCase().charAt(0) + campo.substring(1, campo.length()).toLowerCase();
        repo.actualizarCampos(campo, publiId, valor);

    }

    
    public void actMedia(List<String> mediaActual, String publiId, List<File> subir) {
        ///Voy a sacar el metodo de borrar media, que todo se maneje aca
        
        ///Desde el frontened se envia mediaActual que es la mediaActual, si se quiere borrar alguna img de la publicacion
        ///la lista mediaActual es una copia de la media de la publicacion pero sin las img a borrar, osea que se actualiza sin ellas
        
        ///Necesito borrar las imagenes que ya no van a estar

        Publicacion publicacion = repo.obtPublxId(publiId);
        List<String> mediaVieja = publicacion.getMedia();

        for(String id: mediaVieja){

            if (!mediaActual.contains(id)) {
                cloud.borrMedia(id);
            }
        }

        ///Le tengo q sacar el id de las urls de las imagenes para pdoer borrarlas
        ///Se hubiera solucionado mas rapido haciendo una clase "media o archivo"


        List<String> urls = cloud.subMedia(subir);
        
        List<String> nmedia = new ArrayList<>(mediaActual);
        nmedia.addAll(urls);
        repo.actMedia(nmedia, publiId);



        
    }


}

