package the_thundercats.spyglassserverapi.domain.models;

import lombok.*;
import the_thundercats.spyglassserverapi.domain.dtos.UserDTO;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typeOfGoal")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nameOfGoal;

    @NonNull
    private String descriptionOfGoal;

    @NonNull
    private String iconPicture;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @PrePersist
    public void onCreate() {
        startDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        dateFormat.format(startDate);
    }

    @NonNull
    private Date endDate;

    @NonNull
    private Double startingDollarAmount;

    @NonNull
    private Double targetDollarAmount;

    @NonNull
    private Double currentDollarAmount;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goal")
    private List<Contribution> contributions;
}
