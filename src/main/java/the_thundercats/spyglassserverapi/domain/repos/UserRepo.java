package the_thundercats.spyglassserverapi.domain.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import the_thundercats.spyglassserverapi.domain.models.User;

public interface UserRepo extends JpaRepository<User, String> {
    User findByEmail(String email);
}
