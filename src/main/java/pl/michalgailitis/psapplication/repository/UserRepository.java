package pl.michalgailitis.psapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.michalgailitis.psapplication.domain.User;

public interface UserRepository extends JpaRepository<User, String> {

}
