package the_thundercats.spyglassserverapi.domain.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceCreationException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.models.User;
import the_thundercats.spyglassserverapi.domain.repos.UserRepo;
import the_thundercats.spyglassserverapi.domain.services.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo){this.userRepo = userRepo;}

    @Override
    public User createUser(User user) throws ResourceCreationException {
        Optional<User> optional = userRepo.findByEmail(user.getEmail());
        if(optional.isPresent())
            throw new ResourceCreationException("User with email exists " + user.getEmail());
        return userRepo.save(user);
    }

    @Override
    public User getUserById(String id) throws ResourceNotFoundException {
        return userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No user with id:" + id));
    }

    @Override
    public User updateUser(String id, User userDetails) throws ResourceNotFoundException, ResourceCreationException {
        User savedUser = getUserById(id);
        savedUser.setFirstName(userDetails.getFirstName());
        savedUser.setLastName(userDetails.getLastName());
        savedUser.setEmail(userDetails.getEmail());
        Optional<User> optional = userRepo.findByEmail(savedUser.getEmail());
        if(optional.isPresent())
            throw new ResourceCreationException("User with email exists " + savedUser.getEmail());
        return userRepo.save(savedUser);
    }

    @Override
    public void deleteUser(String id) throws ResourceNotFoundException {
        userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No user with id: " + id));
        User user = getUserById(id);
        userRepo.delete(user);
    }
}
