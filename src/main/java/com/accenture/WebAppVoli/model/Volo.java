package com.accenture.WebAppVoli.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="voli")
public class Volo {

    @Id
    @GeneratedValue
    private Integer codice;

    private String compagnia;
    private String aeroportoPartenza;
    private String aeroportoDestinazione;
    private LocalDate data;
    private LocalTime orarioDecollo;
    private LocalTime orarioAtterraggio;
    private int postiDisponibili;


}
