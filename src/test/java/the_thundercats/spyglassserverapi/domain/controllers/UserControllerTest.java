package the_thundercats.spyglassserverapi.domain.controllers;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.dtos.UserDTO;
import the_thundercats.spyglassserverapi.domain.models.User;
import the_thundercats.spyglassserverapi.domain.services.UserService;
import the_thundercats.spyglassserverapi.security.PrincipalDetailsArgumentResolver;
import the_thundercats.spyglassserverapi.security.models.FireBaseUser;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private User mockUser;
    private User mockUserDTO;
    private User savedUser;
    private User savedUserDTO;
    private User savedUser2;
    private User savedUserDTO2;

    @BeforeEach
    public void setUp() {
        FireBaseUser fireBaseUser = new FireBaseUser();
        fireBaseUser.setEmail("rose@gmail.com");
        fireBaseUser.setUid("qazqaz");
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(userService))
                .setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver(fireBaseUser))
                .build();

        mockUser = new User("sabrina", "rose", "bwina@gmail.com");
        mockUserDTO = new UserDTO(mockUser);

        savedUser = new User("sabrina", "rose", "bwina@gmail.com");
        savedUser.setId("rose1234y");
        savedUserDTO = new UserDTO(savedUser);

        savedUser2 = new User("luis", "alejandro", "luis@gmail.com");
        savedUser2.setId("lx777");
        savedUserDTO2 = new UserDTO(savedUser2);


    }

    @Test
    @DisplayName("POST - success")
    public void createTest01() throws Exception {
        BDDMockito.doReturn(savedUserDTO).when(userService).createUser(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BaseControllerTest.asJsonString(mockUser)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is("rose1234y")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Is.is("sabrina")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Is.is("rose")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("bwina@gmail.com")));
    }

    @Test
    @DisplayName("GET by id - success")
    public void getByIdTest01() throws Exception {
        BDDMockito.doReturn(savedUserDTO).when(userService).getUserById("rose1234y");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/rose1234y"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is("rose1234y")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Is.is("sabrina")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Is.is("rose")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("bwina@gmail.com")));
    }

    @Test
    @DisplayName("GET by id - failed")
    public void getByIdTest02() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(userService).getUserById("rose1234y");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/rose1234y"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Update - success")
    public void updateTest01() throws Exception {
        User updateUser = new User("bwina", "rolls", "bwinatech@gmail.com");
        updateUser.setId("rose1234y");
        UserDTO updateUserDTO = new UserDTO(updateUser);
        BDDMockito.doReturn(updateUserDTO).when(userService).updateUser(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/rose1234y")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BaseControllerTest.asJsonString(updateUserDTO)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is("rose1234y")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Is.is("bwina")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Is.is("rolls")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("bwinatech@gmail.com")));
    }

    @Test
    @DisplayName("Update - failed")
    public void updateTest02() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(userService).updateUser(any(), any());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/rose1234y")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BaseControllerTest.asJsonString(mockUser)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Delete - success")
    public void deleteTest01() throws Exception {
        BDDMockito.doNothing().when(userService).deleteUser(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/rose1234y"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Delete - failed")
    public void deleteTest02() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not found")).when(userService).deleteUser(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/rose1234y"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}