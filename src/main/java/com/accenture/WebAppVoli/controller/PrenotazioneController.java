package com.accenture.WebAppVoli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.WebAppVoli.model.Prenotazione;
import com.accenture.WebAppVoli.service.PrenotazioneService;

@RestController
@RequestMapping("/voli")
@CrossOrigin(origins = "*")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @PostMapping("/{idVolo}/prenotazioni")
    public Prenotazione prenota(
            @PathVariable Integer idVolo,
            @RequestBody Integer posti,
            Authentication authentication) {

        String username = authentication.getName(); // utente autenticato dal JWT
        return prenotazioneService.prenota(idVolo, posti, username);
    }
}

/*
2) Come lo useresti

Supponiamo di avere:

GET /voli/5

che restituisce:

{
    "codice": 5,
    "compagnia": "Ryanair",
    "partenza": "Bari",
    "destinazione": "Roma",
    "postiDisponibili": 100
}

L'utente vuole prenotare 3 posti.

Fa:

POST localhost:8080/prenotazioni/5

Body:

3

Il flusso sarà:

POST /prenotazioni/5

        |
        v

PrenotazioneController

        |
        v

prenotazioneService.prenota(5,3)

        |
        v

Cerca volo con id 5

        |
        v

Controlla posti disponibili

        |
        v

Crea Prenotazione

        |
        v

Aggiorna Volo

        |
        v

Salva nel DB
*/