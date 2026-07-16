package com.accenture.WebAppVoli.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VoloDto {
    @NotEmpty(message = "la compagnia non può essere vuota")
    private String compagnia;
    @NotEmpty(message = "l'aeroporto di partenza non può essere vuoto")
    private String aeroportoPartenza;
    @NotEmpty(message = "l'aeroporto di destinazione non può essere vuoto")
    private String aeroportoDestinazione;
    @NotEmpty(message = "la data non può essere vuota")
    private LocalDate data;
    @NotEmpty(message = "l'orario di parenza non può essere vuoto")
    private LocalTime orarioDecollo;
    @NotEmpty(message = "l'orario di arrivo non può essere vuoto")
    private LocalTime orarioAtterraggio;
    @NotBlank(message = "posti non disponibili non può essere vuoto")
    private int postiDisponibili;
}
