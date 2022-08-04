package the_thundercats.spyglassserverapi.domain.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceCreationException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.dtos.UserCreateRequest;
import the_thundercats.spyglassserverapi.domain.dtos.UserDTO;
import the_thundercats.spyglassserverapi.domain.models.User;
import the_thundercats.spyglassserverapi.domain.services.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/new")
    public ResponseEntity<UserDTO> create(@RequestBody UserCreateRequest user) throws ResourceCreationException {
        UserDTO userSave = userService.createUser(user);
        return new ResponseEntity<>(userSave, HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") String id) throws ResourceNotFoundException {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> update(@PathVariable("id")String id, @RequestBody User userProfile) throws ResourceNotFoundException {
        UserDTO userDTO = userService.updateUser(id, userProfile);
        return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") String id) throws ResourceNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
