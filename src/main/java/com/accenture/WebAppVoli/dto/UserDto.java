package com.accenture.WebAppVoli.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty(message = "il nome non può essere vuoto")
    private String nome;
    @NotEmpty(message = "il cognome non può essere vuoto")
    private String cognome;
    @NotEmpty(message = "l'indirizzo non può essere vuoto")
    private String indirizzo;
    @NotBlank(message = "formato codice fiscale non valido")
    private String cf;
    @NotEmpty(message = "l'username non può essere vuoto")
    private String username;
    @NotEmpty(message = "la password non può essere vuota")
    private String password;

}
