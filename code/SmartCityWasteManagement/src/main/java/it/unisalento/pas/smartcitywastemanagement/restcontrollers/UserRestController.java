package it.unisalento.pas.smartcitywastemanagement.restcontrollers;

import it.unisalento.pas.smartcitywastemanagement.domain.User;
import it.unisalento.pas.smartcitywastemanagement.dto.UserDTO;
import it.unisalento.pas.smartcitywastemanagement.exceptions.UserNotFoundException;
import it.unisalento.pas.smartcitywastemanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * classe che gestisce gli utenti e quidni le loro query tramite chiamate REST
 */

@RestController
@RequestMapping("/api/users")  // per mappare in "/api/users/" le visualizzazioni che rappresentano gli users
public class UserRestController {

    @Autowired
    UserRepository userRepository;

    /**
     * metodo GET per visualizzare tutti gli utenti
     * @return di tutti gli users presenti nel db
     */
    @RequestMapping(value="/", method= RequestMethod.GET) // per mappare la GET della ricerca degli users in ".../"
    public List<UserDTO> getAll(){

        List<UserDTO> users = new ArrayList<>();

        for(User user: userRepository.findAll()){           // prendo uno user dal db e lo istanzio come oggetto
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
     * metodo GET per visualizzare uno user corrispondente ad un certo id
     * @param id
     * @return dell'utente corrispondete all'id passato come parametro
     * @throws UserNotFoundException
     */
    @PreAuthorize("hasRole('ADMIN')") // per far accedere all'URI sono chi ha role=ADMIN
    @RequestMapping(value="/{id}", method=RequestMethod.GET) // per mappare la GET di uno user tramite l'id
                                                                // presente nei parametri (@PathVariable) in ".../{id}"
    public UserDTO get(@PathVariable String id) throws UserNotFoundException {

        System.out.println("api/users/id of user1 -> " + id);

        Optional<User> optUser = userRepository.findById(id); // cerco nel db l'utente con l'id passato come parametro

        if(optUser.isPresent()){                              // se è stato trovato lo restituisce (in questo caso è mock)
            // mock:
            UserDTO user1 = new UserDTO();
            user1.setName("Jhonny");
            user1.setSurname("Stecchino");
            user1.setEmail("jhonnystecchino@sium.com");
            user1.setAge(69);

            return user1;
        }

        throw new UserNotFoundException();
    }

    /**
     * metodo POST per inserire un nuovo user nel db
     * @param userDTO
     * @return dell'utente che è stato creato
     */
    @RequestMapping (value="/", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
                            // per mappare la POST di uno user preso da parametro (@RequestBody) in ".../" in formato JSON
    public UserDTO post(@RequestBody UserDTO userDTO){

        User newUser = new User();                      // creazione del nuovo user
        newUser.setName(userDTO.getName());
        newUser.setSurname(userDTO.getSurname());
        newUser.setEmail(userDTO.getEmail());
        newUser.setAge(userDTO.getAge());

        newUser = userRepository.save(newUser);         // salvataggio dell'utente nel db + return dello user con
                                                        // relativo id assegnato da Spring grazie alla notazione "@Id"
        System.out.println("New user id: " + newUser.getId());

        userDTO.setId(newUser.getId());                 // update dell'id del relativo oggetto user istanziato prima
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

        List<User> result = userRepository.findBySurname(surname); // cerco nel db gli utenti tramite il campo surname
        List<UserDTO> users = new ArrayList<>();               // istanzio la lista di "utenti oggetto"

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
}
