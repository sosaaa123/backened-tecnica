package com.tecnica.Conexion;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConexionCloud {

    private Cloudinary cloudinary;

    public ConexionCloud() {

        Dotenv dotenv = Dotenv.load();
        String cloudinaryUrl = dotenv.get("CLOUDINARY_URL");

        this.cloudinary = new Cloudinary(cloudinaryUrl);
    }


    ///Lo cambio a try-catch, necesito si o si que me devuelva una lista (revisar servicio)
    public List<String> subMedia(List<File>archivos){

        try {

            List<String> urls = new ArrayList<>();
            for(File archivo: archivos){

            ///Cloudinary devuelve un objeto map con informacion de la imagen, yo solo necesito la url
            Map res = cloudinary.uploader().upload(archivo, ObjectUtils.asMap("folder", "Tecnica-Imagenes"));
            String resUrl = (String) res.get("url");
            urls.add(resUrl);

        }

        return urls;
            
        } catch (IOException e) {
           List<String> error =  new ArrayList<>();
           ///Fijarme que tipo de dato es error, le hago cast y lo meto(cambie de )
           error.add(" ");
           return error;
        }
        
    }


    public List<String> obtUrlsId (List<String> urls){
        List<String> urlsId = new ArrayList<>();

        for (String url: urls){
            ///https://res.cloudinary.com/tu-cloud/image/upload/v1620000000/tuimagen.jpg url tipo
            ///public id: v1620000000
            
            String publicId = url.substring(url.indexOf("upload/") + 7, url.lastIndexOf(".")); ///Ver si esto devuelve lo que quiero
            urlsId.add(publicId);
        }
        
        return urlsId;
    }

    public void borrMedia(String publicId) {
        try {
             cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

        } catch (IOException e) {
            System.out.println(e);
        }

       

        
    }
}
