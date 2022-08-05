package the_thundercats.spyglassserverapi.domain.services;

import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.models.Contribution;

import java.util.List;

public interface ContributionService {
    Contribution create(Contribution contribution);
    Contribution getById(Long id) throws ResourceNotFoundException;
    List<Contribution> getAll();
    void delete(Long id) throws ResourceNotFoundException;
}
