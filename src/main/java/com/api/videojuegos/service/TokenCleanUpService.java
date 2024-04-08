package com.api.videojuegos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.api.videojuegos.repository.TokenRepository;

import java.util.Date;

@Service
public class TokenCleanUpService {

	@Autowired
    private TokenRepository tokenRepository; // Asumiendo que tienes un repositorio para los tokens

    @Scheduled(cron = "0 */2 * * * *") // Ejecutar cada dos minutos
    public void cleanupExpiredTokens() {
        System.out.println("Ejecutando limpieza de tokens vencidos...");
        Date currentDate = new Date();
        tokenRepository.deleteExpiredTokens(currentDate);
        System.out.println("Limpieza de tokens vencidos completada.");
    }
}