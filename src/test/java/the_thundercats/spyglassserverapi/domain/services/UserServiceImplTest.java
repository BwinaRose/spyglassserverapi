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
import the_thundercats.spyglassserverapi.domain.Frequency;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceCreationException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.dtos.UserCreateRequest;
import the_thundercats.spyglassserverapi.domain.dtos.UserDTO;
import the_thundercats.spyglassserverapi.domain.models.RecurringGoal;
import the_thundercats.spyglassserverapi.domain.models.User;
import the_thundercats.spyglassserverapi.domain.repos.UserRepo;
import the_thundercats.spyglassserverapi.security.services.FirebaseUserMgrService;

import java.util.ArrayList;
import java.util.Date;
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
    private UserDTO savedUser01;

    @BeforeEach
    public void setup(){
        mockUser = new User("Sabrina", "Rose", "bwina@gmail.com");
        savedUser01 = new UserDTO(mockUser);
        savedUser01.setId("bwinaID");
    }

    @Test
    @DisplayName("get user by id - success")
    public void getByIdTest01() throws ResourceNotFoundException {
        BDDMockito.doReturn(Optional.of(savedUser01)).when(userRepo).findById("bwinaID");
        User user = userService.getUserById("bwinaID");
        Assertions.assertNotNull(user);

    }
    @Test
    @DisplayName("get user by id - fail")
    public void getByIdTest02(){
        BDDMockito.doReturn(Optional.empty()).when(userRepo).findById("bwinaID");
        Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            userService.getUserById("bwinaID");
        });
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
