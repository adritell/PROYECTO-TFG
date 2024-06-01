package com.api.videojuegos.dto;


import lombok.Data;
import java.util.Set;

@Data
public class UsuarioAdminResponse {
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private boolean activo;
    private Set<String> roles;
    
    
    public UsuarioAdminResponse() {
    }

    public UsuarioAdminResponse(Long id, String nombre, String apellidos, String email, boolean activo, Set<String> roles) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.activo = activo;
        this.roles = roles;
    }
    
    
    public UsuarioAdminResponse(Long id, String nombre, String apellidos, String email, boolean activo, Set<String> roles, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.activo = activo;
        this.roles = roles;
        this.password = password;
    }
}