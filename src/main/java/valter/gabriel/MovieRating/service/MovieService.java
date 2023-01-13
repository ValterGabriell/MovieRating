package valter.gabriel.MovieRating.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import valter.gabriel.MovieRating.domain.Movie;
import valter.gabriel.MovieRating.domain.User;
import valter.gabriel.MovieRating.domain.UserMovie;
import valter.gabriel.MovieRating.domain.dto.AddMovieRate;
import valter.gabriel.MovieRating.domain.dto.UserMoviePK;
import valter.gabriel.MovieRating.domain.movie.MovieModel;
import valter.gabriel.MovieRating.handle.ApiRequestException;
import valter.gabriel.MovieRating.repo.MovieRepo;
import valter.gabriel.MovieRating.repo.UserMovieRepo;
import valter.gabriel.MovieRating.repo.UsersRepo;
import valter.gabriel.MovieRating.utils.Utilities;

import java.util.List;
import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepo movieRepo;
    private final UserMovieRepo userMovieRepo;
    private final UsersRepo usersRepo;


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

    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepo.findAll(pageable);
    }

    public Double totalAverageRate(Float movieId) {
        Stream<UserMovie> userMovieStream = userMovieRepo.findAll()
                .stream()
                .filter(movie -> movie.getUserMoviePK().getMovieId().equals(movieId));

        return userMovieStream
                .flatMapToDouble(movie -> DoubleStream.of(movie.getRating()))
                .average().getAsDouble();
    }


    public UserMovie updateMovieRate(AddMovieRate addMovieRate, Float movieId) {
        User userByToken = Utilities.getUserByToken(usersRepo);
        UserMovie movie = userByToken.getUserMovies().stream()
                .filter(_movie -> _movie.getUserMoviePK().getMovieId().equals(movieId))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Filme não encontrado"));

        movie.setRating(addMovieRate.getRate());

        userByToken.getUserMovies().add(movie);
        usersRepo.save(userByToken);
        return movie;
    }

    public User addRateToMovie(AddMovieRate addMovieRate, Float movieId) {
        ModelMapper mapper = new ModelMapper();


        //recebendo filme e salvando ele na entidade de UserMovie
        Movie movie = movieRepo.findById(movieId).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Movie not found"));
        UserMovie userMovie = mapper.map(movie, UserMovie.class);


        //criando chave estrangeira e setando no objeto userMovie
        UserMoviePK userMoviePK = new UserMoviePK(Utilities.getUserByToken(usersRepo).getUserId(), movieId);
        userMovie.setUserMoviePK(userMoviePK);
        userMovie.setRating(addMovieRate.getRate());

        Utilities.getUserByToken(usersRepo).getUserMovies().add(userMovie);

        usersRepo.save(Utilities.getUserByToken(usersRepo));
        return Utilities.getUserByToken(usersRepo);
    }

    public String deleteMovie(Float movieId) {

        UserMoviePK userMoviePK = new UserMoviePK(Utilities.getUserByToken(usersRepo).getUserId(), movieId);
        UserMovie userMovie = userMovieRepo.findById(userMoviePK).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Movie not found"));

        userMovieRepo.delete(userMovie);
        return "Filme " + userMovie.getTitle() + " deletado com sucesso";
    }
}
