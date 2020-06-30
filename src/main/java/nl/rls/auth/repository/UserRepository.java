package nl.rls.auth.repository;

import nl.rls.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAllByOwnerId(Integer ownerId);
}
