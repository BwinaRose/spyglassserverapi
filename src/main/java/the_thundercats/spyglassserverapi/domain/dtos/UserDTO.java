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

    public UserDTO(User user){
        id = user.getId();
        fullName = String.format("%s %s",user.getFirstName(),user.getLastName());
    }
}
