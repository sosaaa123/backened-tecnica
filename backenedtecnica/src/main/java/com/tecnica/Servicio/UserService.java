package com.tecnica.Servicio;
import java.util.List;

import com.password4j.Hash;
import com.password4j.Password;
import com.tecnica.Modelos.Usuario;
import com.tecnica.Repositorio.UserRep;
import com.tecnica.TokenManager.TokenManager;

public class UserService {
    private UserRep repositorio;
    private TokenManager TokenManager;

    public UserService(UserRep repositorio, TokenManager tokenMg){
        this.repositorio = repositorio;
        this.TokenManager = tokenMg;
    }

    public List<Usuario> verUsuarios(){return repositorio.verUsuarios();}

    public void crearUsuario(Usuario usuario){
        ///Para encriptar contraseña
        String cont = usuario.getContra();

         String hash = Password.hash(cont)
                          .addRandomSalt()
                          .withPBKDF2()
                          .getResult();

        usuario.setContra(hash);

        repositorio.crear(usuario);
    }

    public void borrarUser(String userId){repositorio.borrarUser(userId);}

    
    ///Es este el metodo q uso para validar
    ///1/8 metodo cambiado de bool a String(devuelve el token como string)
    ///Valida y envia el token
    public String validar(String correo, String cont) {

        ///Primero me fijo si el correo existe
        ///Si no existe ese correo usuario me trae null
        Usuario usuario = repositorio.obtUserxCorr(correo);

        if (usuario == null) {
            return null;
        }

        ///Passwordcheck compara la contraseña q llega con la del usuario encontrado con ese email en PBKDF2 
        boolean contValidada = Password.check(cont, usuario.getContra()).withPBKDF2();

        if(contValidada){
            return TokenManager.generarToken(correo); ///Retorno el token (ver como lo manejo en el controller)
        }

        else{
            return null;
        }

        
    }

    public boolean autorizar(String token, String correo) {
        return TokenManager.authorize(token, correo);
    }

    public Usuario obtenerPorId(String id) {
        return repositorio.obtUserxId(id);
    }

    public void actualizar(String campo, String userId, String nuevoValor){
        ///No creo por ahora implementar esta funcion en el frontened, la pongo para completar y en caso de "emergencia"
        ///Capaz despues borro el metodo actualizar contraseña o agregarle mas metodos de seguridad o hacerlo un metodo aparte
        if(campo.equalsIgnoreCase("Contrasena")){
            Hash hash = Password.hash(nuevoValor).withPBKDF2();
            String nValor = hash.getResult();
            repositorio.actualizar(campo, userId, nValor);
        }
        
         else{
            repositorio.actualizar(campo, userId, nuevoValor);
         }
    }

    
}
