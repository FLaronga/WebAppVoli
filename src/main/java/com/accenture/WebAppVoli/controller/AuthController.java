package com.accenture.WebAppVoli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.WebAppVoli.dto.LoginDto;
import com.accenture.WebAppVoli.dto.UserDto;
import com.accenture.WebAppVoli.exception.NotFoundException;
import com.accenture.WebAppVoli.model.User;
import com.accenture.WebAppVoli.service.AuthService;
import com.accenture.WebAppVoli.service.UserService;

import jakarta.validation.ValidationException;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public User register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (s,e)->s+e));
        }

        return userService.saveUser(userDto);
    }

    @GetMapping("/auth/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException{
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (s,e)->s+e));
        }

        /*
        1. verificare che l'utente esiste
        2. se l'utente non esite, lancia una eccezione
        3. se l'utente esiste, generare il token e inviarlo al client
         */

        return authService.login(loginDto);
    }

    // @GetMapping("/auth/login")
    // public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
    //     if(bindingResult.hasErrors()){
    //         throw new ValidationException(bindingResult.getAllErrors().stream().
    //                 map(objectError -> objectError.getDefaultMessage()).
    //                 reduce("", (s,e)->s+e));
    //     }

    //     /*
    //     1. verificare che l'utente esiste
    //     2. se l'utente non esite, lancia una eccezione
    //     3. se l'utente esiste, generare il token e inviarlo al client
    //      */

    //     return authService.login(loginDto);
    // }

}
