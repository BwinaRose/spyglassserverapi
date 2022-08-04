package the_thundercats.spyglassserverapi.domain.models;

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
@Table(name = "messages")
public class Contribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Double contributionAmount;
    @ManyToOne
    private Date contributionDate;
    @NonNull
    private Goal goal;

    @NonNull
    Date date = new Date();

    @Override
    public String toString() {
        return String.format("%d %2.2f %s %s", id, contributionAmount, contributionDate, goal);
    }

    @PrePersist
    protected void onCreate(){
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        dateFormat.format(date);
    }
}
