package com.accenture.WebAppVoli.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

/*
1) inserimento dei dati di un volo (ADMIN)
2) la loro modifica o cancellazione (ADMIN)
3) ricerca degli voli (ciascuno con le relative informazioni) relativi ad un certo aeroporto di partenza/destinazione o ad una certa data o ad una certa compagnia (TUTTI I RUOLI)
4) ricerca di tutti i voli (TUTTI I RUOLI)
5) prenotazione di un volo anche per più posti (TUTTI I RUOLI)
*/

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Lazy
    @Autowired
    private JwtFilter jwtFilter; //---

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
        .formLogin(form -> form.disable())
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(Customizer.withDefaults())



    .authorizeHttpRequests(auth -> auth

        .requestMatchers("/auth/**").permitAll()

        // Ricerca voli
        .requestMatchers(HttpMethod.GET, "/voli/**")
            .hasAnyAuthority("USER", "ADMIN")

        .requestMatchers(HttpMethod.POST, "/prenotazioni/**")
            .hasAnyAuthority("USER", "ADMIN")

        // Inserimento
        .requestMatchers(HttpMethod.POST, "/voli")
            .hasAuthority("ADMIN")

        // Modifica
        .requestMatchers(HttpMethod.PUT, "/voli/**")
            .hasAuthority("ADMIN")

        // Eliminazione
        .requestMatchers(HttpMethod.DELETE, "/voli/**")
            .hasAuthority("ADMIN")

        // Prenotazione
        .requestMatchers(HttpMethod.POST, "/voli/*/prenotazioni")
            .hasAnyAuthority("USER", "ADMIN")

        .anyRequest().authenticated()
    ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); //--
        

    return httpSecurity.build();
}

    // regole cors
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permette le richieste da qualsiasi frontend (es. Live Server)
        configuration.setAllowedOrigins(List.of("*")); 
        
        // Permette tutti i metodi HTTP
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Permette tutti gli header (es. Content-Type, Authorization per i token futuri)
        configuration.setAllowedHeaders(List.of("*"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Applica questa configurazione a tutte le rotte del backend
        source.registerCorsConfiguration("/**", configuration); 
        
        return source;
    }
}
