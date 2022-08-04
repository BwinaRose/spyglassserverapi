package the_thundercats.spyglassserverapi.domain.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceCreationException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.models.User;
import the_thundercats.spyglassserverapi.domain.repos.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    private User mockUser;
    private User savedUser01;

    @BeforeEach
    public void setup(){
        mockUser = new User("Sabrina", "Rose", "bwina@gmail.com");

        savedUser01 = new User("Sabrina", "Rose", "bwina@gmail.com");
        savedUser01.setId("1");
    }

    @Test
    @DisplayName("createUser - success")
    public void createUserTest01() throws ResourceCreationException {
        BDDMockito.doReturn(Optional.empty()).when(userRepo).findById(ArgumentMatchers.any());
        BDDMockito.doReturn(savedUser01).when(userRepo).save(mockUser);
        User user = userService.createUser(mockUser);
        Assertions.assertNotNull(user.getId());
    }

    @Test
    @DisplayName("createUser - fail")
    public void createUserTest02(){

    }

    @Test
    @DisplayName("get user by id - success")
    public void getByIdTest01() throws ResourceNotFoundException {
        BDDMockito.doReturn(Optional.of(savedUser01)).when(userRepo).findById("1");
        User user = userService.getUserById("1");
        Assertions.assertNotNull(user);

    }
    @Test
    @DisplayName("get user by id - fail")
    public void getByIdTest02(){
        BDDMockito.doReturn(Optional.empty()).when(userRepo).findById("1");
        Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            userService.getUserById("1");
        });
    }

    @Test
    @DisplayName("updateUser - pass")
    public void updateTest01(){
    }

    @Test
    @DisplayName("updateUser - fail")
    public void updateTest02(){
    }

    @Test
    @DisplayName("deleteUser - pass")
    public void deleteTest01(){

    }

    @Test
    @DisplayName("deleteUser - fail")
    public void deleteTest02(){
        BDDMockito.doReturn(Optional.empty()).when(userRepo).findById("1");
        Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            userService.getUserById("1");
        });
    }
}
