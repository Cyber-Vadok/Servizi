package it.unisalento.pas.smartcitywastemanagement.service;

import it.unisalento.pas.smartcitywastemanagement.domain.User;
import it.unisalento.pas.smartcitywastemanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * classe che (per best practice) contiene solo busienss logic dell'utente
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  /**
   * usiamo il repository per la gestione dell'autorizzazione
   */
  @Autowired
  UserRepository userRepository;

  /**
   * per prelevare gli user dal db
   * @param username the username identifying the user whose data is required.
   * @return
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = userRepository.findByUsername(username);

    if(user == null){
      throw new UsernameNotFoundException(username);
    }

    // oggetto utente indipendete dalla logica di business e serve a spring per funzionare
    UserDetails userDetails = org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPassword())
        .roles("USER") // tipologi dell'utente (autorizzazione)
        .build();

    return userDetails;
  }
}
