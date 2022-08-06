package the_thundercats.spyglassserverapi.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import the_thundercats.spyglassserverapi.domain.models.Goal;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode
@Table(name = "contributions")
public class Contribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Double contributionAmount;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date contributionDate;
    @ManyToOne
    @JsonBackReference
    private Goal goal;

    @Override
    public String toString() {
        return String.format("%d %2.2f %s %s", id, contributionAmount, contributionDate, goal);
    }

    @PrePersist
    public void onCreate() {
        contributionDate = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        contributionDate = new Date();
    }
}
