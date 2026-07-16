package com.accenture.WebAppVoli.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "prenotazioni")
public class Prenotazione {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Volo volo;

    private Integer postiPrenotati;
}
