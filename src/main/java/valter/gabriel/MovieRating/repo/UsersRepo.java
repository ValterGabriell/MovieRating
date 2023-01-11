package valter.gabriel.MovieRating.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import valter.gabriel.MovieRating.domain.User;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
