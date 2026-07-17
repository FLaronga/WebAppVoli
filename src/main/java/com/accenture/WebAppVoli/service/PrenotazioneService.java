package com.accenture.WebAppVoli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.accenture.WebAppVoli.model.Prenotazione;
import com.accenture.WebAppVoli.model.User;
import com.accenture.WebAppVoli.model.Volo;
import com.accenture.WebAppVoli.repository.PrenotazioneRepository;
import com.accenture.WebAppVoli.repository.UserRepository;
import com.accenture.WebAppVoli.repository.VoloRepository;

import jakarta.transaction.Transactional;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private VoloRepository voloRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional // rende l'operazione atomica
    public Prenotazione prenota(Integer idVolo, Integer posti, String username) {

        // 1) Recupero il volo
        Volo volo = voloRepository.findById(idVolo)
                .orElseThrow(() -> new RuntimeException("Volo non trovato"));


        // 2) Controllo disponibilità posti
        if(volo.getPostiDisponibili() < posti) {
            throw new RuntimeException("Posti insufficienti");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));


        // 4) Creo prenotazione
        Prenotazione prenotazione = new Prenotazione();

        prenotazione.setUser(user);
        prenotazione.setVolo(volo);
        prenotazione.setPostiPrenotati(posti);


        // 5) Aggiorno posti disponibili
        volo.setPostiDisponibili(
            volo.getPostiDisponibili() - posti
        );


        // 6) Salvo
        voloRepository.save(volo);

        return prenotazioneRepository.save(prenotazione);
    }

/*
    username + password
            |
            ↓
    JWT creato
            |
            ↓
    richiesta POST prenotazione
            |
            ↓
    JwtFilter
            |
            ↓
    SecurityContext
            |
            ↓
    PrenotazioneService
*/
}


