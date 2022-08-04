package the_thundercats.spyglassserverapi.domain.services;

import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceCreationException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.dtos.UserCreateRequest;
import the_thundercats.spyglassserverapi.domain.dtos.UserDTO;
import the_thundercats.spyglassserverapi.domain.models.User;

public interface UserService {
    UserDTO createUser(UserCreateRequest user) throws ResourceCreationException;
    UserDTO getUserById(String id) throws ResourceNotFoundException;
    UserDTO updateUser(String id, User userDetail) throws ResourceNotFoundException;
    void deleteUser(String id) throws ResourceNotFoundException;
}