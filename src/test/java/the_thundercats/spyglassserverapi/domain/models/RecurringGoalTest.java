package the_thundercats.spyglassserverapi.domain.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import the_thundercats.spyglassserverapi.domain.Frequency;

import java.util.Date;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RecurringGoalTest {

    @Test
    @DisplayName("Add to current amount test")
    public void addToCurrentDollarAmountTest01() {
        RecurringGoal goal = new RecurringGoal("travel", "this is for travel", "this would be a path", new Date(), 0.00, 100.00, 0.00, Frequency.WEEKLY);
        Double expected = 10.00;
        goal.addToCurrentDollarAmount(10.00);
        Double actual = goal.getCurrentDollarAmount();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Remove from current amount test")
    public void removeFromCurrentDollarAmountTest() {
        RecurringGoal goal = new RecurringGoal("travel", "this is for travel", "this would be a path", new Date(), 0.00, 100.00, 100.00, Frequency.WEEKLY);
        Double expected = 90.00;
        goal.removeFromCurrentDollarAmount(10.00);
        Double actual = goal.getCurrentDollarAmount();
        Assertions.assertEquals(expected, actual);
    }
}
