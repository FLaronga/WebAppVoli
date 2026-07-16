package com.accenture.WebAppVoli.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.accenture.WebAppVoli.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User implements UserDetails{

    @Id
    @GeneratedValue
    private Integer id;
    private String nome;
    private String cognome;
    private String indirizzo;
    private String cf;
    

    @Column(unique = true) //creerà il campo username univoco sul db, cioè non potrà avere duplicati
    private String username;

    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    //questo metodo ritorna tutti i ruoli che l'utente ha
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Prenotazione> prenotazioni = new ArrayList<>();
}
