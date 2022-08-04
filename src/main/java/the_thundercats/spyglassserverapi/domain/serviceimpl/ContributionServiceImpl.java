package the_thundercats.spyglassserverapi.domain.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.models.Contribution;
import the_thundercats.spyglassserverapi.domain.repos.ContributionRepo;
import the_thundercats.spyglassserverapi.domain.services.ContributionService;

import java.util.List;

@Service
public class ContributionServiceImpl implements ContributionService {
    private ContributionRepo contributionRepo;
    @Autowired
    public ContributionServiceImpl(ContributionRepo contributionRepo) {
        this.contributionRepo = contributionRepo;
    }

    @Override
    public Contribution create(Contribution contribution){
        return contributionRepo.save(contribution);
    }

    @Override
    public Contribution getById(Long id) throws ResourceNotFoundException {
        return contributionRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No Contribution with id: " + id));
    }

    @Override
    public List<Contribution> getByReceiver(Long receiver){
        return contributionRepo.findByReceiver(receiver);
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
