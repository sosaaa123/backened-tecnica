package com.tecnica.TokenManager;

import io.javalin.http.ForbiddenResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

///Recien ahora veo q existe esto, quizas me hubiera servido antes. Lo importo para la expiracion de tokens (1/8)
import java.util.Date;

import javax.crypto.SecretKey;


///Voy a hacer la autenticacion de JWT https://dev.to/iuriimednikov/home-made-jwt-authentication-for-javalin-2pen
///Si no funciona revisar la doc de Javalin sobre lo mismo  y hacerlo asi
///Igual voy a manejar la SECRET_KEY para el JWT como variable de entorno

///Estoy  usando una version vieja de jjwt pq anterior a la 0.12.0 los metodos q uso me los tira como deprecated
public class TokenManager {

    private SecretKey secret_key;

    ///Recibe la variable de ent
    public TokenManager(String key) {
    ///Crea la secret_key
    this.secret_key = Keys.hmacShaKeyFor(key.getBytes());
    }


    

    public String generarToken(String correo) {

        long hora = 3600000; ///Una hora en milisegundos(ver si es lo correcto)
        long tAhora = System.currentTimeMillis();//Tiempo actual en milisegundos (Unix Epoch, q locura)

        Date ttAhora = new Date(tAhora);
        Date expirar = new Date(tAhora + hora);
        
        return Jwts.builder()
            .setSubject(correo)
            .setIssuedAt(ttAhora) ///Cuando se emite el token
            .setExpiration(expirar) ///Cuando expira
            .signWith(secret_key, SignatureAlgorithm.HS256)
            .compact();
    }



    ///Compruebo q el token q recibo tenga el mismo correo q recibo
    public boolean authorize(String token, String correo) {
        try {

            String subject = Jwts.parserBuilder()
                                 .setSigningKey(secret_key)
                                 .build()
                                 .parseClaimsJws(token) ///Valida la firma y decodifica para la autorizacion
                                 .getBody()
                                 .getSubject();
                                 
            return subject.equalsIgnoreCase(correo);

        } catch (Exception ex){
            throw new ForbiddenResponse(); ///Excepcion de javalin(ver link arriba) si no me sirve en el controller usar otra cosa
        }
    }

    ///Funcion para obtener el correo del token, me va a servir 
    public String obtCorrToken(String token) {
    return Jwts.parserBuilder()
               .setSigningKey(secret_key)
               .build()
               .parseClaimsJws(token)
               .getBody()
               .getSubject();
}



}

