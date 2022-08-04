package the_thundercats.spyglassserverapi.domain.services;

import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceCreationException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.models.RecurringGoal;

import java.util.List;

public interface RecurringGoalService {
    RecurringGoal create(String userId, RecurringGoal recurringGoal) throws ResourceNotFoundException;
    RecurringGoal getById(Long id) throws ResourceNotFoundException;
    List<RecurringGoal> getAllFromUser(String userId) throws ResourceNotFoundException;
    RecurringGoal update(Long goalId, RecurringGoal goalDetails) throws ResourceNotFoundException;
    void delete(Long goalId) throws ResourceNotFoundException;
}
