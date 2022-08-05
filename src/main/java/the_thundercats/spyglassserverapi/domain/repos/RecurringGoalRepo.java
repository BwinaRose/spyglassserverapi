package the_thundercats.spyglassserverapi.domain.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import the_thundercats.spyglassserverapi.domain.models.RecurringGoal;
import the_thundercats.spyglassserverapi.domain.models.User;

import java.util.List;

public interface RecurringGoalRepo extends JpaRepository<RecurringGoal, Long> {
    List<RecurringGoal> findByUser(User user);
}
