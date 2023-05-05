package it.unisalento.pas.smartcitywastemanagement.configuration;

import it.unisalento.pas.smartcitywastemanagement.security.JwtAuthenticationFilter;
import it.unisalento.pas.smartcitywastemanagement.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * classe per gestire la sicurezza degli endpoint
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    /**
     * metodo per cryptare le password
     * @return di una nuova implementazione dell'encode della password tramite BCrypt
     */
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * classe per estrapolare l'authentication manager dalla configuration
     * @param authenticationConfiguration
     * @return authentication manager
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * metodo per configurare i filtri sulla sicurezza
     * @param http
     * @return di un filtro che permette tutti gli accessi in /api/bins/** e chiedere autenticazione in /api/users/**
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable() // csrf: cross side request forgery: vulnerabilità dove i browser conservano i cookie
            .authorizeRequests()
            .requestMatchers("/api/users/authenticate").permitAll() // filtra le richieste che matchano il path (authorized url)
            .requestMatchers("/api/users/register").permitAll().
            anyRequest().authenticated().and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // creazione sessione stateless

        // aggiunta del filtro
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * metodo per creare un oggetto che usa un filtro una sola volta in una sola richesta
     * @return oggetto che usa il filtro
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    /**
     * metodo per l'autenticazione dello user passato per @Autowire
     * @return provider dell'autorizzazione
     */
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
