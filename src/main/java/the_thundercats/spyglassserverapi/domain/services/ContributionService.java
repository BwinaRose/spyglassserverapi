package the_thundercats.spyglassserverapi.domain.services;

import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceCreationException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceUpdateException;
import the_thundercats.spyglassserverapi.domain.models.Contribution;
import the_thundercats.spyglassserverapi.domain.models.Goal;

import java.util.List;

public interface ContributionService {

    Contribution create(Long goalId, Contribution contribution) throws ResourceNotFoundException;
    Contribution getById(Long id) throws ResourceNotFoundException;
    List<Contribution> getAllFromGoal(Goal goal) throws ResourceNotFoundException;
    Contribution update(Long id, Contribution contributionDetails) throws ResourceNotFoundException, ResourceUpdateException;
    void delete(Long id) throws ResourceNotFoundException;
}
