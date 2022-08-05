package the_thundercats.spyglassserverapi.domain.services;

import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceCreationException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.models.Goal;
import the_thundercats.spyglassserverapi.domain.models.RecurringGoal;
import the_thundercats.spyglassserverapi.domain.models.User;

import java.util.List;

public interface RecurringGoalService {
    RecurringGoal create(String userId, RecurringGoal recurringGoal) throws ResourceNotFoundException;
    RecurringGoal getById(Long id) throws ResourceNotFoundException;
    List<RecurringGoal> getAllFromUser(User user) throws ResourceNotFoundException;
    RecurringGoal update(Long goalId, Goal goalDetails) throws ResourceNotFoundException;
    void delete(Long goalId) throws ResourceNotFoundException;
}
