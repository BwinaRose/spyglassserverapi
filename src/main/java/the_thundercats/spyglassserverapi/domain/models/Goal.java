package the_thundercats.spyglassserverapi.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Cascade;
import the_thundercats.spyglassserverapi.domain.dtos.UserDTO;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "goal_type")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "goals")
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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date startDate;

    @PrePersist
    public void onCreate() {
        startDate = new Date();
    }

    @NonNull
    private Date endDate;

    @NonNull
    private Double startingDollarAmount;

    @NonNull
    private Double targetDollarAmount;

    @NonNull
    private Double currentDollarAmount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goal")
    private List<Contribution> contributions;
}
