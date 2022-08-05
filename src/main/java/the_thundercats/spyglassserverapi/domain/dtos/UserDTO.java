package the_thundercats.spyglassserverapi.domain.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import the_thundercats.spyglassserverapi.domain.models.User;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@ToString
public class UserDTO extends User {
    private String id;
    private String fullName;
     private String firstName;
     private String lastName;
     private String email;

    public UserDTO(User user){
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        fullName = String.format("%s %s",user.getFirstName(),user.getLastName());
    }
}
