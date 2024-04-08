package com.api.videojuegos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.videojuegos.service.ConfirmacionCorreoService;

@RestController
@RequestMapping("/api/v1/confirmacion")
public class ConfirmacionCorreoController {
    
    @Autowired
    private ConfirmacionCorreoService confirmacionCorreoService;
    
    // Endpoint para confirmar el correo electrónico con el token
    @GetMapping("/confirmar")
    public ResponseEntity<String> confirmarCorreo(@RequestParam("token") String token) {
        boolean confirmacionExitosa = confirmacionCorreoService.confirmarCorreo(token);
        
        if (confirmacionExitosa) {
            return ResponseEntity.ok("Correo electrónico confirmado exitosamente");
        } else {
            return ResponseEntity.badRequest().body("Token de confirmación inválido");
        }
    }
}
