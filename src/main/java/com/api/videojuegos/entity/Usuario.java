package com.api.videojuegos.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

/*La interfaz UserDetails es necesaria para implementarla autorización */
@Entity
public class Usuario implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String apellidos;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name = "activo", nullable = false)
    @NotNull
    private boolean activo;
    @Column(name = "token_confirmacion", length = 100, unique = true)
    private String tokenConfirmacion;
    @ElementCollection(fetch = FetchType.EAGER, targetClass = Rol.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_rol")
    @Column(name = "RolesUser")
    private Set<Rol> roles = new HashSet<>();
    
    
    @OneToMany(mappedBy = "usuario")
    private Set<Seguidores> seguidores;

    @OneToMany(mappedBy = "usuario")
    private Set<ActividadUsuario> actividades;
    
    @OneToMany(mappedBy = "usuarioDestinatario", cascade = CascadeType.ALL)
    private Set<Notificacion> notificaciones;
    
    
    @OneToMany(mappedBy = "usuario")
    private Set<Comentario> comentarios; // Nueva relación con comentarios
    
    @ManyToMany(mappedBy = "usuariosFavoritos")
    private Set<Videojuegos> videojuegosFavoritos; // Nueva relación con videojuegos favoritos

    @Transactional
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Cargar la colección de roles de manera temprana
        roles.size(); // Esto carga la colección de roles

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return nombre;
    }

    public void setFirstName(String firstName) {
        this.nombre = firstName;
    }

    public String getLastName() {
        return apellidos;
    }

    public void setLastName(String lastName) {
        this.apellidos = lastName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getTokenConfirmacion() {
		return tokenConfirmacion;
	}

	public void setTokenConfirmacion(String tokenConfirmacion) {
		this.tokenConfirmacion = tokenConfirmacion;
	}
	
	
    
    
}
