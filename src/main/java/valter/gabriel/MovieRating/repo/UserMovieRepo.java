package valter.gabriel.MovieRating.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import valter.gabriel.MovieRating.domain.UserMovie;
import valter.gabriel.MovieRating.domain.dto.UserMoviePK;


public interface UserMovieRepo extends JpaRepository<UserMovie, UserMoviePK> {
}
