package com.api.videojuegos.service;

import com.api.videojuegos.dto.JwtAuthenticationResponse;
import com.api.videojuegos.request.LoginRequest;
import com.api.videojuegos.request.RegistroRequest;

/* De aqui deberia seguir el flujo y guardar el nuevo registro en la base de datos en caso de un registro de usuario 
 * la entidad user debe implementar la interfaz user details, por su parte el login el AuthenticationService buscará al usuario
 * correspondiente en la base de datos y finalmente generará el token y lo devolverá al AuthenticationController, que será 
 * el encargado de devolver la respuesta al cliente ahora con el token en el cuerpo del mensaje*/
public interface AuthenticationService {
    
    /** REGISTRO */
    void signup(RegistroRequest request);
    
    /** ACCESO a Token JWT */
    JwtAuthenticationResponse signin(LoginRequest request);
}
