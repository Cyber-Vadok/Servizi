package it.unisalento.pas.smartcitywastemanagement.restcontrollers;

import it.unisalento.pas.smartcitywastemanagement.domain.User;
import it.unisalento.pas.smartcitywastemanagement.dto.AuthenticationResponseDTO;
import it.unisalento.pas.smartcitywastemanagement.dto.LoginDTO;
import it.unisalento.pas.smartcitywastemanagement.dto.UserDTO;
import it.unisalento.pas.smartcitywastemanagement.exceptions.UserNotFoundException;
import it.unisalento.pas.smartcitywastemanagement.repositories.UserRepository;
import it.unisalento.pas.smartcitywastemanagement.security.JwtUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static it.unisalento.pas.smartcitywastemanagement.configuration.SecurityConfig.passwordEncoder;

/**
 * classe che gestisce gli utenti e quidni le loro query tramite chiamate REST
 */
@RestController
@RequestMapping("/api/users")  // per mappare in "/api/users/" le visualizzazioni che rappresentano gli users
public class UserRestController {

    @Autowired
    UserRepository userRepository;

    /**
     * per gestire le autenticazioni (a monte del progetto applicativo)
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * per gestire l'jwt
     */
    @Autowired
    private JwtUtilities jwtUtilities;

    /**
     * metodo GET per visualizzare tutti gli utenti
     * @return di tutti gli users presenti nel db
     */
    @RequestMapping(value="/", method= RequestMethod.GET) // per mappare la GET della ricerca degli users in ".../"
    public List<UserDTO> getAll(){

        List<UserDTO> users = new ArrayList<>();

        // prendo uno user dal db e lo istanzio come oggetto
        for(User user: userRepository.findAll()){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setSurname(user.getSurname());
            userDTO.setEmail(user.getEmail());
            userDTO.setAge(user.getAge());
            userDTO.setUsername(user.getUsername());

            users.add(userDTO);
        }

        return users;
    }

    /**
     * metodo GET per visualizzare uno user corrispondente ad un certo id
     * @param id
     * @return dell'utente corrispondete all'id passato come parametro
     * @throws UserNotFoundException
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") // per far accedere all'URI sono chi ha role=ADMIN
    @RequestMapping(value="/{id}", method=RequestMethod.GET) // per mappare la GET di uno user tramite l'id
                                                                // presente nei parametri (@PathVariable) in ".../{id}"
    public UserDTO get(@PathVariable String id) throws UserNotFoundException {

        System.out.println("api/users/id of user1 -> " + id);

        // cerco nel db l'utente con l'id passato come parametro
        Optional<User> optUser = userRepository.findById(id);

        // se è stato trovato lo restituisce (in questo caso è mock)
        if(optUser.isPresent()){
            User user = optUser.get();

            UserDTO user1 = new UserDTO();
            user1.setName(user.getName());
            user1.setSurname(user.getSurname());
            user1.setEmail(user.getEmail());
            user1.setAge(user.getAge());
            user1.setId(user.getId());

            return user1;
        }
        else {
            //throw new UserNotFoundException();

            UserDTO user1 = new UserDTO();
            
            user1.setName("ciao");
            user1.setSurname("ciao");
            user1.setEmail("ciao");
            user1.setAge(40);
            user1.setId("ciao");

            return user1;
        }
    }

    /**
     * metodo POST per inserire un nuovo user nel db
     * @param userDTO
     * @return dell'utente che è stato creato
     */
    @RequestMapping (value="/register", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
                        // per mappare la POST di uno user preso da parametro (@RequestBody) in ".../" in formato JSON
    public UserDTO post(@RequestBody UserDTO userDTO){

        // creazione del nuovo user
        User newUser = new User();
        newUser.setName(userDTO.getName());
        newUser.setSurname(userDTO.getSurname());
        newUser.setEmail(userDTO.getEmail());
        newUser.setAge(userDTO.getAge());
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder().encode(userDTO.getPassword()));

        // salvataggio dell'utente nel db + return dello user con relativo id
        // assegnato da Spring grazie alla notazione "@Id"
        newUser = userRepository.save(newUser);
        System.out.println("New user id: " + newUser.getId());

        // update dell'id del relativo oggetto user istanziato prima
        userDTO.setId(newUser.getId());
        userDTO.setPassword(null);
        return userDTO;
    }

    /**
     * metodo GET per la ricerca di utenti tramite surname
     * @param surname
     * @return di una lista di utenti che matchano col surname
     */
    @RequestMapping(value="/find", method=RequestMethod.GET) // per mappare la GET della ricerca di users che abbiano
                                       // il surname passato per parametro passato dalla URI (@RequestParam) in ".../find"
    public List<UserDTO> findBySurname(@RequestParam String surname){

        // cerco nel db gli utenti tramite il campo surname
        List<User> result = userRepository.findBySurname(surname);

        // istanzio la lista di "utenti oggetto"
        List<UserDTO> users = new ArrayList<>();

        for(User user: result){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setSurname(user.getSurname());
            userDTO.setEmail(user.getEmail());
            userDTO.setAge(user.getAge());

            users.add(userDTO);
        }

        return users;
    }

    /**
     * metodo per gestire il tipo di dato restituito da un API: usato per effettuare l'autenticazione
     * @param loginDTO
     * @return risposta del autenticazione con il token appena creato
     */
    @RequestMapping(value="/authenticate", method=RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO loginDTO){

        // chiede a spring se l'utente è autorizzato oppure no
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword()
            )
        );

        // è buona norma caricarsi l'utente per capire se l'utente esiste (doppio controllo)
        User user = userRepository.findByUsername(authentication.getName());


        if(user == null){
            throw new UsernameNotFoundException(loginDTO.getUsername());
        }

        // context che vale a livello di thread quindi se chiamo della business logic
        // potranno accedere al contex; invece la volta successiva che effettuiamo la
        // chiamata, il context cambia
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generazione token
        final String jwt = jwtUtilities.generateToken(user.getUsername());

        return ResponseEntity.ok(new AuthenticationResponseDTO(jwt));
    }
}
