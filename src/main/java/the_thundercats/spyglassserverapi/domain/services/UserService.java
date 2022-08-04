package the_thundercats.spyglassserverapi.domain.services;

import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceCreationException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.models.User;

public interface UserService {
    User createUser(User user) throws ResourceCreationException;
    User getUserById(String id) throws ResourceNotFoundException;
    User updateUser(String id, User userDetails) throws ResourceNotFoundException,ResourceCreationException;
    void deleteUser(String id) throws ResourceNotFoundException;
}