package valter.gabriel.MovieRating.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import valter.gabriel.MovieRating.domain.Movie;


public interface MovieRepo extends JpaRepository<Movie, Float> {

}
