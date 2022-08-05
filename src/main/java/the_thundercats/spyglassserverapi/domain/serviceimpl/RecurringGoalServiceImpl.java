package the_thundercats.spyglassserverapi.domain.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceCreationException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.dtos.UserDTO;
import the_thundercats.spyglassserverapi.domain.models.RecurringGoal;
import the_thundercats.spyglassserverapi.domain.models.User;
import the_thundercats.spyglassserverapi.domain.repos.RecurringGoalRepo;
import the_thundercats.spyglassserverapi.domain.services.RecurringGoalService;
import the_thundercats.spyglassserverapi.domain.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class RecurringGoalServiceImpl implements RecurringGoalService {

    private RecurringGoalRepo recurringGoalRepo;
    private UserService userService;

    @Autowired
    public RecurringGoalServiceImpl(RecurringGoalRepo recurringGoalRepo, UserService userService) {
        this.recurringGoalRepo = recurringGoalRepo;
        this.userService = userService;
    }

    @Override
    public RecurringGoal create(String userId, RecurringGoal recurringGoal) throws ResourceNotFoundException {
        User user = userService.getUserById(userId);
        recurringGoal.setUser(user);
        recurringGoal.getUser().setId(userId);
        return recurringGoalRepo.save(recurringGoal);
    }

    @Override
    public RecurringGoal getById(Long id) throws ResourceNotFoundException {
        Optional<RecurringGoal> recurringGoalOptional = recurringGoalRepo.findById(id);
        if(recurringGoalOptional.isEmpty()) {
            throw new ResourceNotFoundException("Recurring goal not found with id: " + id);
        }
        return recurringGoalOptional.get();
    }

    @Override
    public List<RecurringGoal> getAllFromUser(String userId) throws ResourceNotFoundException {
        return recurringGoalRepo.findByUser(userId);
    }

    @Override
    public RecurringGoal update(Long goalId, RecurringGoal goalDetails) throws ResourceNotFoundException {
        RecurringGoal recurringGoal = getById(goalId);
        recurringGoal.setNameOfGoal(goalDetails.getNameOfGoal());
        recurringGoal.setDescriptionOfGoal(goalDetails.getDescriptionOfGoal());
        recurringGoal.setIconPicture(goalDetails.getIconPicture());
        recurringGoal.setStartDate(goalDetails.getStartDate());
        recurringGoal.setEndDate(goalDetails.getEndDate());
        recurringGoal.setCurrentDollarAmount(goalDetails.getCurrentDollarAmount());
        recurringGoal.setStartingDollarAmount(goalDetails.getStartingDollarAmount());
        recurringGoal.setTargetDollarAmount(goalDetails.getTargetDollarAmount());
        return recurringGoalRepo.save(recurringGoal);
    }

    @Override
    public void delete(Long goalId) throws ResourceNotFoundException {
        RecurringGoal recurringGoal = getById(goalId);
        recurringGoalRepo.delete(recurringGoal);
    }
}
