package valter.gabriel.MovieRating.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import valter.gabriel.MovieRating.domain.UserMovie;


public interface UserMovieRepo extends JpaRepository<UserMovie, Float> {
}
