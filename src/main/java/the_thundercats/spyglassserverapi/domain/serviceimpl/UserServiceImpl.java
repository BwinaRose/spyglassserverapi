package the_thundercats.spyglassserverapi.domain.serviceimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceCreationException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.dtos.UserCreateRequest;
import the_thundercats.spyglassserverapi.domain.dtos.UserDTO;
import the_thundercats.spyglassserverapi.domain.models.User;
import the_thundercats.spyglassserverapi.domain.repos.UserRepo;
import the_thundercats.spyglassserverapi.domain.services.UserService;
import the_thundercats.spyglassserverapi.security.services.FirebaseUserMgrService;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;

    private FirebaseUserMgrService firebaseUserMgrService;

    @Autowired
    public UserServiceImpl(UserRepo userProfileRepo,
                                  FirebaseUserMgrService firebaseUserMgrService) {
        this.userRepo = userProfileRepo;
        this.firebaseUserMgrService = firebaseUserMgrService;
    }

    @Override
    public UserDTO createUser(UserCreateRequest detailDTO) throws ResourceCreationException {
        Optional<User> optional = userRepo.findByEmail(detailDTO.getEmail());
        if(optional.isPresent())
            throw new ResourceCreationException("User exists");
        log.info(detailDTO.toString());
        String uid= firebaseUserMgrService.createFireBaseUser(detailDTO);
        User user = new User(detailDTO.getFirstName(), detailDTO.getLastName(), detailDTO.getEmail());
        user.setId(uid);
        user = userRepo.save(user);
        log.debug("Created User with id {} and email {}", user.getId(), user.getEmail());
        return new UserDTO(user);
    }

    @Override
    public UserDTO getUserById(String id) throws ResourceNotFoundException {
        User user = userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(""));
        return new UserDTO(user);
    }

    @Override
    public UserDTO updateUser(String id, User userDetail) throws ResourceNotFoundException {
        User user = retrieveById(userDetail.getId());
        firebaseUserMgrService.updateFireBaseUser(userDetail);
        user.setEmail(userDetail.getEmail());
        user.setFirstName(userDetail.getFirstName());
        user.setLastName(userDetail.getLastName());
        user = userRepo.save(user);
        return new UserDTO(user);
    }

    @Override
    public void deleteUser(String id) throws ResourceNotFoundException {
        User user = retrieveById(id);
        firebaseUserMgrService.deleteFireBaseUser(id);
        userRepo.delete(user);
    }

    private User retrieveById(String id) throws ResourceNotFoundException {
        User user = userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(""));
        return user;
    }
}
