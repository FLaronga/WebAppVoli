package com.accenture.WebAppVoli.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.accenture.WebAppVoli.exception.NotFoundException;
import com.accenture.WebAppVoli.model.User;
import com.accenture.WebAppVoli.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTool {
    //classe gestita direttamente da Spring per la gestione del token
    @Value("${token.expiration}")
    private long durata;

    @Value("${token.secret}")
    private String chiaveSegreta;

    @Autowired
    private UserService userService;

    public String createToken(User user){
        //per generare il token avremo bisogno della data di generazione del token, della durata e dell'id
        //dell'utente per il quale stiamo creando il token. Avremo inoltre bisogno anche della chiave segreta
        //per poter crittografare il token

        return Jwts.builder().issuedAt(new Date()).
                expiration(new Date(System.currentTimeMillis()+durata)).
                subject(String.valueOf(user.getId())).
                claim("role", user.getRole().name()). // ---
                signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).
                compact();
    }


    //metodo per la verifica della validità del token
    public void validateToken(String token){
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).
                build().parse(token);
    }

    public User getUserFromToken(String token) throws NotFoundException {
        //recuperare l'id dell'utente dal token
        int id = Integer.parseInt(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).
                build().parseSignedClaims(token).getPayload().getSubject());

        return userService.getUser(id);
    }
}
