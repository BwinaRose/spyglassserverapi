package the_thundercats.spyglassserverapi.domain.controllers;


import com.fasterxml.jackson.annotation.JsonFormat;
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
import the_thundercats.spyglassserverapi.domain.Frequency;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.dtos.UserDTO;
import the_thundercats.spyglassserverapi.domain.models.RecurringGoal;
import the_thundercats.spyglassserverapi.domain.models.User;
import the_thundercats.spyglassserverapi.domain.services.RecurringGoalService;
import the_thundercats.spyglassserverapi.security.PrincipalDetailsArgumentResolver;
import the_thundercats.spyglassserverapi.security.models.FireBaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class RecurringGoalControllerTest {

    @MockBean
    private RecurringGoalService mockRecurringGoalService;

    @Autowired
    private MockMvc mockMvc;

    private RecurringGoal mockGoal01;
    private RecurringGoal savedGoal01;
    private RecurringGoal savedGoal02;

    private UserDTO savedUser01;
    private User mockUser;

    @BeforeEach
    public void setUp() {
        FireBaseUser fireBaseUser = new FireBaseUser();
        fireBaseUser.setEmail("test@auser.com");
        fireBaseUser.setUid("xyz");
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RecurringGoalController(mockRecurringGoalService))
                .setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver(fireBaseUser))
                .build();

        Date toReturn = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        String stringDate= dateFormat.format(toReturn);

        mockGoal01 = new RecurringGoal("travel", "this is for travel", "this would be a path", new Date(), 0.00, 100.00, 0.00, Frequency.WEEKLY);
        mockGoal01.setUser(savedUser01);

        savedGoal01 = new RecurringGoal("travel", "this is for travel", "this would be a path", new Date(), 0.00, 100.00, 0.00, Frequency.WEEKLY);
        savedGoal01.setId(1L);
        savedGoal01.setUser(savedUser01);

        savedGoal02 = new RecurringGoal("car", "this is for car", "this would be a path", new Date(), 0.00, 10000.00, 100.00, Frequency.MONTHLY);
        savedGoal02.setId(1L);

        mockUser = new User("Sabrina", "Rose", "bwina@gmail.com");

        savedUser01 = new UserDTO(mockUser);
        savedUser01.setId("abc");
    }

    @Test
    @DisplayName("POST - success") // doesn't compare date
    public void createTest01() throws Exception {
        BDDMockito.doReturn(savedGoal01).when(mockRecurringGoalService).create(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/recurringgoals/abc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BaseControllerTest.asJsonString(mockGoal01)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameOfGoal", Is.is("travel")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descriptionOfGoal", Is.is("this is for travel")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.iconPicture", Is.is("this would be a path")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startingDollarAmount", Is.is(0.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.targetDollarAmount", Is.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentDollarAmount", Is.is(0.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contributionFrequency", Is.is(Frequency.WEEKLY.name())));
    }

    @Test
    @DisplayName("GET by id - success")
    public void getByIdTest01() throws Exception {
        BDDMockito.doReturn(savedGoal01).when(mockRecurringGoalService).getById(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recurringgoals/1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameOfGoal", Is.is("travel")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descriptionOfGoal", Is.is("this is for travel")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.iconPicture", Is.is("this would be a path")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startingDollarAmount", Is.is(0.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.targetDollarAmount", Is.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentDollarAmount", Is.is(0.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contributionFrequency", Is.is(Frequency.WEEKLY.name())));
    }

    @Test
    @DisplayName("GET by id - failed")
    public void getByIdTest02() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(mockRecurringGoalService).getById(1L);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recurringgoals/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("PUT - success")
    public void updateTest01() throws Exception {
        RecurringGoal updated = new RecurringGoal("update", "this is for an update", "this would be a path", new Date(), 230.00, 100.00, 0.00, Frequency.WEEKLY);
        updated.setId(1L);
        BDDMockito.doReturn(updated).when(mockRecurringGoalService).update(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/recurringgoals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BaseControllerTest.asJsonString(updated)))

                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameOfGoal", Is.is("update")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descriptionOfGoal", Is.is("this is for an update")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.iconPicture", Is.is("this would be a path")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startingDollarAmount", Is.is(230.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.targetDollarAmount", Is.is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentDollarAmount", Is.is(0.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contributionFrequency", Is.is(Frequency.WEEKLY.name())));
    }

    @Test
    @DisplayName("PUT - failed")
    public void updateTest02() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(mockRecurringGoalService).update(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/recurringgoals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BaseControllerTest.asJsonString(mockGoal01)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("DELETE - success")
    public void deleteTest01() throws Exception {
        BDDMockito.doNothing().when(mockRecurringGoalService).delete(any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/recurringgoals/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE - failed")
    public void deleteTest02() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(mockRecurringGoalService).delete(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/recurringgoals/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
