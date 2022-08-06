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
import the_thundercats.spyglassserverapi.domain.Frequency;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.models.Contribution;
import the_thundercats.spyglassserverapi.domain.models.RecurringGoal;
import the_thundercats.spyglassserverapi.domain.services.ContributionService;
import the_thundercats.spyglassserverapi.security.PrincipalDetailsArgumentResolver;
import the_thundercats.spyglassserverapi.security.models.FireBaseUser;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ContributionControllerTest {

    @MockBean
    private ContributionService contributionService;

    @Autowired
    private MockMvc mockMvc;

    private Contribution mockContribution01;
    private Contribution savedContribution01;
    private Contribution savedContribution02;
    private RecurringGoal savedGoal01;

    @BeforeEach
    public void setUp() {

        FireBaseUser fireBaseUser = new FireBaseUser();
        fireBaseUser.setEmail("test@auser.com");
        fireBaseUser.setUid("xyz");
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ContributionController(contributionService))
                .setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver(fireBaseUser))
                .build();

        savedGoal01 = new RecurringGoal("travel", "this is for travel", "this would be a path", new Date(), 0.00, 100.00, 0.00, Frequency.WEEKLY);
        savedGoal01.setId(1L);

        mockContribution01 = new Contribution(100.00);
        mockContribution01.setGoal(savedGoal01);

        savedContribution01 = new Contribution(100.00);
        savedContribution01.setId(1L);
        savedContribution01.setGoal(savedGoal01);

        savedContribution02 = new Contribution(150.00);
        savedContribution02.setId(1L);
    }

    @Test
    @DisplayName("POST - success")
    public void createTest01() throws Exception {
        BDDMockito.doReturn(savedContribution01).when(contributionService).create(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/contribution/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BaseControllerTest.asJsonString(mockContribution01)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contributionAmount", Is.is(100.00)));
    }

    @Test
    @DisplayName("GET by id - success")
    public void getByIdTest01() throws Exception {
        BDDMockito.doReturn(savedContribution01).when(contributionService).getById(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contribution/1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contributionAmount", Is.is(100.00)));
    }

    @Test
    @DisplayName("GET by id - failed")
    public void getByIdTest02() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(contributionService).getById(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contribution/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Update - success")
    public void updateTest01() throws Exception {
        Contribution updated = new Contribution(150.00);
        updated.setId(1L);
        BDDMockito.doReturn(updated).when(contributionService).update(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/contribution/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BaseControllerTest.asJsonString(updated)))

                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contributionAmount", Is.is(150.00)));
    }

    @Test
    @DisplayName("Update - failed")
    public void updateTest02() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not FOund")).when(contributionService).update(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/contribution/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BaseControllerTest.asJsonString(mockContribution01)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Delete - success")
    public void deleteTest01() throws Exception {
        BDDMockito.doNothing().when(contributionService).delete(any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/contribution/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Delete - failed")
    public void deleteTest02() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not found")).when(contributionService).delete(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/contribution/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
