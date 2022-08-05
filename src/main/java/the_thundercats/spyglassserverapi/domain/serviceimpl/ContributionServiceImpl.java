package the_thundercats.spyglassserverapi.domain.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.models.Contribution;
import the_thundercats.spyglassserverapi.domain.models.Goal;
import the_thundercats.spyglassserverapi.domain.models.RecurringGoal;
import the_thundercats.spyglassserverapi.domain.repos.ContributionRepo;
import the_thundercats.spyglassserverapi.domain.services.ContributionService;
import the_thundercats.spyglassserverapi.domain.services.RecurringGoalService;

import java.util.List;

@Service
public class ContributionServiceImpl implements ContributionService {
    private ContributionRepo contributionRepo;
    private RecurringGoalService recurringGoalService;
    @Autowired
    public ContributionServiceImpl(ContributionRepo contributionRepo, RecurringGoalService recurringGoalService) {
        this.contributionRepo = contributionRepo;
        this.recurringGoalService = recurringGoalService;
    }

    @Override
    public Contribution create(Long goalId, Contribution contribution) throws ResourceNotFoundException {
        Goal goal = recurringGoalService.getById(goalId);
        goal.addToCurrentDollarAmount(contribution.getContributionAmount());
        recurringGoalService.update(goalId, goal);
        contribution.setGoal(goal);
        return contributionRepo.save(contribution);
    }

    @Override
    public Contribution getById(Long id) throws ResourceNotFoundException {
        return contributionRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No Contribution with id: " + id));
    }

    @Override
    public List<Contribution> getAll() {
        return contributionRepo.findAll();
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        contributionRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No Message with id: " + id));
        Contribution message = getById(id);
        contributionRepo.delete(message);
    }
}
