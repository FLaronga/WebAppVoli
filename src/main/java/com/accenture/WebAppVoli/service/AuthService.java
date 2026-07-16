package com.accenture.WebAppVoli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.accenture.WebAppVoli.dto.LoginDto;
import com.accenture.WebAppVoli.model.User;
import com.accenture.WebAppVoli.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTool JwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
        1. verificare che l'utente esiste
        2. se l'utente non esite, lancia una eccezione
        3. se l'utente esiste, generare il token e inviarlo al client
    */

    public String login(LoginDto loginDto) throws NotFoundException {
        User user = userRepository.findByUsername(loginDto.getUsername()).
                orElseThrow(() -> new com.accenture.WebAppVoli.exception.NotFoundException("Utente con questo username/password non trovato"));

        System.out.println("Username: " + loginDto.getUsername());
        System.out.println("Password DB: " + user.getPassword());
        if(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            //utente è autenticato, devo creare il token
            return jwtTool.createToken(user);
        }
        else{
            throw new com.accenture.WebAppVoli.exception.NotFoundException("Utente con questo username/password non trovato");
        }
    }
}
