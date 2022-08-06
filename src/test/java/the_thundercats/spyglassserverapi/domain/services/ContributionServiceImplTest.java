package the_thundercats.spyglassserverapi.domain.services;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import the_thundercats.spyglassserverapi.domain.Frequency;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.models.Contribution;
import the_thundercats.spyglassserverapi.domain.models.RecurringGoal;
import the_thundercats.spyglassserverapi.domain.repos.ContributionRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ContributionServiceImplTest {

    @Autowired
    private ContributionService contributionService;

    @MockBean
    private RecurringGoalService recurringGoalService;

    @MockBean
    private ContributionRepo contributionRepo;

    private Contribution mockContribution01;
    private Contribution savedContribution01;
    private Contribution savedContribution02;
    private RecurringGoal savedGoal01;

    @BeforeEach
    public void setUp() {
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
    @DisplayName("Create contribution - success")
    public void createTest01() {
        BDDMockito.doReturn(savedGoal01).when(recurringGoalService).getById(1L);
        BDDMockito.doReturn(savedContribution01).when(contributionRepo).save(mockContribution01);
        Contribution contribution = contributionService.create(1L, mockContribution01);
        Assertions.assertEquals(savedContribution01, contribution);
    }

    @Test
    @DisplayName("Get by id - success")
    public void getByIdTest01() {
        BDDMockito.doReturn(Optional.of(savedContribution01)).when(contributionRepo).findById(1L);
        Contribution contribution = contributionService.getById(1L);
        Assertions.assertEquals(savedContribution01, contribution);
    }

    @Test
    @DisplayName("Get by id - failed")
    public void getByIdTest02() {
        BDDMockito.doReturn(Optional.empty()).when(contributionRepo).findById(1L);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            contributionService.getById(1L);
        });
    }

    @Test
    @DisplayName("Get all from goal - success")
    public void getAllFromGoalTest() {
        List<Contribution> contributionList = new ArrayList<>();
        contributionList.add(savedContribution01);
        BDDMockito.doReturn(contributionList).when(contributionRepo).findByGoal(savedGoal01);
        List<Contribution> actual = contributionService.getAllFromGoal(savedGoal01);
        Assertions.assertIterableEquals(contributionList, actual);
    }

    @Test
    @DisplayName("Update - success")
    public void updateTest01() {
        Contribution updated = new Contribution(500.00);
        updated.setId(1L);
        BDDMockito.doReturn(Optional.of(savedContribution01)).when(contributionRepo).findById(1L);
        BDDMockito.doReturn(updated).when(contributionRepo).save(savedContribution01);
        Contribution actual = contributionService.update(1L, updated);
        Assertions.assertEquals(updated, actual);
    }
}
