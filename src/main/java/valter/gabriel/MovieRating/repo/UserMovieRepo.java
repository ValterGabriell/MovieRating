package valter.gabriel.MovieRating.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import valter.gabriel.MovieRating.domain.Movie;
import valter.gabriel.MovieRating.domain.UserMovieRate;


public interface UserMovieRepo extends JpaRepository<UserMovieRate, Float> {
}
