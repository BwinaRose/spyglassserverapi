package the_thundercats.spyglassserverapi.domain.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import the_thundercats.spyglassserverapi.domain.models.RecurringGoal;
import the_thundercats.spyglassserverapi.domain.models.User;

import java.util.List;

public interface RecurringGoalRepo extends JpaRepository<RecurringGoal, Long> {
    List<RecurringGoal> findByUser(String id);
}
