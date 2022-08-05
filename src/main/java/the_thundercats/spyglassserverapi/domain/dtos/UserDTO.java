package the_thundercats.spyglassserverapi.domain.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import the_thundercats.spyglassserverapi.domain.models.Goal;
import the_thundercats.spyglassserverapi.domain.models.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Getter
@ToString
public class UserDTO extends User {
    private String id;
    private String fullName;
     private String firstName;
     private String lastName;
     private String email;

     private List<Goal> goals;

    public UserDTO(User user){
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        goals = user.getGoals();
        fullName = String.format("%s %s",user.getFirstName(),user.getLastName());
    }
}
