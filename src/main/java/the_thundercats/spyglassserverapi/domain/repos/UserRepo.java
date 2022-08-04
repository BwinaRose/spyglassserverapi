package the_thundercats.spyglassserverapi.domain.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import the_thundercats.spyglassserverapi.domain.models.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
