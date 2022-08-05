package the_thundercats.spyglassserverapi.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date endDate;

    @NonNull
    private Double startingDollarAmount;

    @NonNull
    private Double targetDollarAmount;

    @NonNull
    private Double currentDollarAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goal")
    @JsonManagedReference
    private List<Contribution> contributions;

    public void addToCurrentDollarAmount(Double amount) {
        currentDollarAmount += amount;
    }
}
