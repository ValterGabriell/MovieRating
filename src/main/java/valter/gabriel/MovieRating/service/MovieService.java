package valter.gabriel.MovieRating.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import valter.gabriel.MovieRating.domain.Movie;
import valter.gabriel.MovieRating.domain.movie.MovieModel;
import valter.gabriel.MovieRating.repo.MovieRepo;
import valter.gabriel.MovieRating.repo.UserMovieRepo;

import java.util.List;
import java.util.Objects;
import java.util.stream.DoubleStream;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepo movieRepo;
    private final UserMovieRepo userMovieRepo;


    public void getMoviesDataFromApi() {
        RestTemplate restTemplate = new RestTemplate();

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.themoviedb.org")
                .path("3/movie/popular")
                .queryParam("api_key", "b7559fc0b921503c97f4ce8e7f906489")
                .queryParam("page", 1)
                .build();

        ResponseEntity<MovieModel> entity = restTemplate.getForEntity(uri.toUriString(), MovieModel.class);

        Objects.requireNonNull(entity.getBody()).getResults().forEach(_movie -> {

            Movie userMovie = new Movie(
                    _movie.getId(),
                    _movie.getTitle(),
                    _movie.getOriginal_language(),
                    _movie.getRelease_date(),
                    _movie.getBackdrop_path()
            );

            movieRepo.save(userMovie);
        });

    }

    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    public Double totalAverageRate(Float movieId) {

        return userMovieRepo.findAll()
                .stream()
                .filter(movie -> movie.getUserMoviePK().getMovieId().equals(movieId))
                .flatMapToDouble(movie -> DoubleStream.of(movie.getRating()))
                .average().getAsDouble();


    }

}
