package it.unisalento.pas.smartcitywastemanagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static it.unisalento.pas.smartcitywastemanagement.security.SecurityConstants.JWT_SECRET;

/**
 * classe per gestione del jwt
 */
@Service
public class JwtUtilities {

    /**
     * metodo per estrarre lo username da un jwt
     * @param token
     * @return il subject del token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * metodo per estrarre il dead time da un jwt
     * @param token
     * @return l'expiration del token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * metodo per estrarre da un jwt un parametro a propria scelta
     * @param token
     * @param claimsResolver paramentro da estrarre
     * @return parametro che si voleva estrarre dal token
     * @param <T>
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * metodo per estrarre tutti i parametri presenti nel body del jwt
     * @param token
     * @return body del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * metodo che verifica se il token è scaduto
     * @param token
     * @return true se è scaduto, false se non è scaduto
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * metodo che genera un token dato uno username e dei claims vuoti
     * @param username
     * @return token vuoto
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * metodo che crea un token con i dati passati per parametro
     * @param claims
     * @param subject
     * @return token buildato
     */
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject) // set up claims e subject del token
            .setIssuedAt(new Date(System.currentTimeMillis())) // imposta il momento in cui è stato creato
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // imposta un dead time
            .signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact(); // crittografia del token con algoritmo e key
    }

    /**
     * metodo che vede se il token passato per parametro è dello stesso utente passato per parametro +
     * vede se il token è scaduto
     * @param token
     * @param userDetails incapsula le informazioni di un utente
     * @return true se è valido, false se non è valido
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}