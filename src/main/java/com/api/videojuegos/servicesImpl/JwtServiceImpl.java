package com.api.videojuegos.servicesImpl;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.api.videojuegos.entity.Rol;
import com.api.videojuegos.entity.Token;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.repository.TokenRepository;
import com.api.videojuegos.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {

	@Autowired
    private TokenRepository tokenRepository;
	
    @Value("${jwt.secret}")
    private String secretKey;

    /* Este método extrae el nombre de usuario del token JWT proporcionado. Devuelve el nombre de usuario 
    extraído del token. Se utiliza para obtener el nombre de usuario de un token JWT.*/
    @Override
    public String extractUserName(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }


    /*Este método extrae todas las reclamaciones del token JWT proporcionado. Utiliza el método 
     parseClaimsJws(token) de la clase Jwts para analizar el token y obtener las reclamaciones contenidas en él. 
     Devuelve un objeto Claims que contiene todas las reclamaciones del token. */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    /*Este método utiliza el método extractAllClaims(token) para extraer la fecha de expiración del token.
    Devuelve la fecha de expiración del token JWT.*/
    private Date extractExpiration(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    /*Este método verifica si un token JWT ha expirado.
    Utiliza el método extractExpiration(token) para obtener la fecha de expiración del token y luego compara
    esa fecha con la fecha actual. Devuelve true si el token ha expirado y false en caso contrario.*/
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    /*Este método verifica si un token JWT es válido. Toma un token JWT y los detalles del usuario para compararlos 
    con la información del token. Devuelve un booleano que indica si el token es válido o no.
    Se utiliza para verificar si un token JWT es válido para un determinado usuario.*/
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        Token storedToken = tokenRepository.findByToken(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token) && storedToken == null;
    }

    
    
    
    @Override
    public String createToken(Usuario user, Instant expirationInstant, Set<Rol> roles) {
        String token = generateToken(user.getEmail(), expirationInstant, roles);
        
        // Crear una nueva entidad Token
        Token tokenEntity = new Token();
        tokenEntity.setUser(user);
        tokenEntity.setToken(token);
        
        // Establecer la fecha de expiración en la entidad Token
        tokenEntity.setExpirationDate(Date.from(expirationInstant));
        
        // Guardar la entidad Token en la base de datos
        tokenRepository.save(tokenEntity);
        
        return token;
    }

    private String generateToken(String userEmail, Instant expirationInstant, Set<Rol> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.stream().map(Enum::name).collect(Collectors.toList()));
        claims.put("expiration", Date.from(expirationInstant));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expirationInstant))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Este método obtiene la clave de firma utilizada para firmar los tokens JWT.
    // La clave de firma se genera a partir del secreto (secretKey) proporcionado como una cadena, utilizando el
    // algoritmo de firma SignatureAlgorithm.HS256. Devuelve un objeto Key que representa la clave de firma.
    private Key getSigningKey() {
        return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

}
