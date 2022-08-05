package the_thundercats.spyglassserverapi.domain.models;

import lombok.*;
import the_thundercats.spyglassserverapi.domain.Frequency;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("recurring")
@ToString
@NoArgsConstructor
public class RecurringGoal extends Goal{
    Frequency contributionFrequency;

    public RecurringGoal(Frequency contributionFrequency) {
        this.contributionFrequency = contributionFrequency;
    }

    public RecurringGoal(@NonNull String nameOfGoal, @NonNull String descriptionOfGoal, @NonNull String iconPicture, @NonNull Date endDate, @NonNull Double startingDollarAmount, @NonNull Double targetDollarAmount, @NonNull Double currentDollarAmount, Frequency contributionFrequency) {
        super(nameOfGoal, descriptionOfGoal, iconPicture, endDate, startingDollarAmount, targetDollarAmount, currentDollarAmount);
        this.contributionFrequency = contributionFrequency;
    }
}
