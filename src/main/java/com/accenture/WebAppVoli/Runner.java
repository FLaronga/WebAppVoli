package com.accenture.WebAppVoli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.accenture.WebAppVoli.enumeration.Role;
import com.accenture.WebAppVoli.model.User;
import com.accenture.WebAppVoli.repository.UserRepository;

@Component
public class Runner implements CommandLineRunner{
    
    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder; // bean definito nella SecurityConfig

    @Override
    public void run(String... args) throws Exception {
        
        // 1. controllo se l'utente admin esiste già nel DB per evitare  duplicazione
        if (userRepository.findByUsername("admin").isEmpty()) {
            
            // 2. creo la nuova istanza del modello User
            User admin = new User();
            admin.setUsername("admin");
            
            // !! la password DEVE essere criptata, altrimenti Spring Security la rifiuterà al login
            admin.setPassword(passwordEncoder.encode("1234"));
            
            // imposto il ruolo
            admin.setRole(Role.ADMIN); 

            // imposto altre info a caso
            admin.setNome("Andrea");
            admin.setCognome("Scalzo");
            admin.setCf("SCLNDR");
            admin.setIndirizzo("Via casuale");

            // 3. salviamo l'utente sul database
            userRepository.save(admin);
            
            System.out.println("!! Utente ADMIN creato con successo nel database !!");
        } else {
            System.out.println("!! Utente ADMIN già presente nel database !!");
            System.out.println(userRepository.findAll());
        }
    }
}