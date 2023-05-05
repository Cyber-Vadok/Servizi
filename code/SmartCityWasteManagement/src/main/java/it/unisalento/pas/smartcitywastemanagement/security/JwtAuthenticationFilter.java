package it.unisalento.pas.smartcitywastemanagement.security;

import it.unisalento.pas.smartcitywastemanagement.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * classe per effettuare un filtro una sola volta in una sola richesta
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * per gestire l'jwt
     */
    @Autowired
    private JwtUtilities jwtUtilities ;

    /**
     * per istanziare i servizi di un utente
     */
    @Autowired
    private CustomUserDetailsService customerUserDetailsService ;

    /**
     * metodo per
     * @param request
     * @param response
     * @param chain catena di filtri
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // estrazione dell'header "Authorization"
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // controllo se ha come prefisso "Bearer "; se si estra il jwt e lo username del suo user
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtilities.extractUsername(jwt);
        }

        // se non ho un contesto
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // carico lo username nel customerUserDetailsService e ne estraggo uno user dal db
            UserDetails userDetails = this.customerUserDetailsService.loadUserByUsername(username);

            // se il jwt è valido si crea un contesto con un costruttore utilizzabile solo da utenti autorizzati
            if (jwtUtilities.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

                // inserisce le informazione della richiesta web nei details dell'utente autorizzato
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // imposto il contesto per appartenere all'utente autorizzato
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // invoco il filtro dalla catena
        chain.doFilter(request, response);
    }

}