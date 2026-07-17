package com.accenture.WebAppVoli.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoloDto {
    @NotEmpty(message = "la compagnia non può essere vuota")
    private String compagnia;
    @NotEmpty(message = "l'aeroporto di partenza non può essere vuoto")
    private String aeroportoPartenza;
    @NotEmpty(message = "l'aeroporto di destinazione non può essere vuoto")
    private String aeroportoDestinazione;
    @NotNull(message = "la data non può essere vuota")
    private LocalDate data;
    @NotNull(message = "l'orario di parenza non può essere vuoto")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime orarioDecollo;
    @NotNull(message = "l'orario di arrivo non può essere vuoto")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime orarioAtterraggio;
    private int postiDisponibili;
}
