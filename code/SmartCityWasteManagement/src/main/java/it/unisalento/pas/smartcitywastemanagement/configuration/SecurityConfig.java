package it.unisalento.pas.smartcitywastemanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * classe per gestire la sicurezza degli endpoint
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * metodo per cryptare le password
     * @return di una nuova implementazione dell'encode della password tramite BCrypt
     */
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * metodo per configurare i filtri sulla sicurezza
     * @param http
     * @return di un filtro che permette tutti gli accessi in /api/bins/** e chiedere autenticazione in /api/users/**
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable() // csrf: cross side request forgery: vulnerabilità dove i browser conservano
                                        // i cookie al loro interno
                .authorizeHttpRequests()
                .requestMatchers("/api/users/**").authenticated() // tutte le richieste che matchano il path devonono
                                                                        // essere autenticate (con "**" và beep di 2 livelli)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/bins/**").permitAll() // tutto quello che sta in questo path non ha resscrizioni
                                                                // di autenticazione
                .and()
                .httpBasic(Customizer.withDefaults())       // autenticazione tramite username e password nell'header
                .build();
    }

    /**
     * metodo mock per creare degli utenti con username, password, roles
     * @return di InMemoryUserDetailsManager per fare lo storage delle info di autenticazione degli utenti
     */
    @Bean
    public UserDetailsService userDetailsService(){

        // mock users:
        UserDetails jhonny = User.builder()
                .username("Jhonny")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN")
                .build();

        UserDetails caca = User.builder()
                .username("Caca")
                .password(passwordEncoder().encode("123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(jhonny, caca);
    }
}
