package it.unisalento.pas.smartcitywastemanagement.restcontrollers;

import it.unisalento.pas.smartcitywastemanagement.domain.User;
import it.unisalento.pas.smartcitywastemanagement.dto.UserDTO;
import it.unisalento.pas.smartcitywastemanagement.exceptions.UserNotFoundException;
import it.unisalento.pas.smartcitywastemanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    UserRepository userRepository;

    // method that return all users
    @RequestMapping(value="/", method= RequestMethod.GET)
    public List<UserDTO> getAll(){

        List<UserDTO> users = new ArrayList<>();

        for(User user: userRepository.findAll()){
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

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public UserDTO get(@PathVariable String id) throws UserNotFoundException {

        System.out.println("api/users/id of user1 -> " + id);

        Optional<User> optUser = userRepository.findById(id);

        if(optUser.isPresent()){
            UserDTO user1 = new UserDTO(); //mock
            user1.setName("Jhonny");
            user1.setSurname("Stecchino");
            user1.setEmail("jhonnystecchino@sium.com");
            user1.setAge(69);

            return user1;
        }

        throw new UserNotFoundException();
    }

    @RequestMapping (value="/", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
    public UserDTO post(@RequestBody UserDTO userDTO){

        User newUser = new User();
        newUser.setName(userDTO.getName());
        newUser.setSurname(userDTO.getSurname());
        newUser.setEmail(userDTO.getEmail());
        newUser.setAge(userDTO.getAge());

        newUser = userRepository.save(newUser); // return updated user (with id)
        System.out.println("New user id: " + newUser.getId());

        userDTO.setId(newUser.getId());
        return userDTO;
    }

    @RequestMapping(value="/find", method=RequestMethod.GET)
    public List<UserDTO> findBySurname(@RequestParam String surname){

        List<User> result = userRepository.findBySurname(surname);
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
}
