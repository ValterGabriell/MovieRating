package valter.gabriel.MovieRating.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.MovieRating.domain.Movie;
import valter.gabriel.MovieRating.domain.User;
import valter.gabriel.MovieRating.domain.UserMovie;
import valter.gabriel.MovieRating.domain.dto.AddMovieRate;
import valter.gabriel.MovieRating.service.MovieService;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/movie/")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;


    @GetMapping("all")
    public ResponseEntity<Page<Movie>> getAllMovies(Pageable pageable) {
        return new ResponseEntity<>(movieService.getAllMovies(pageable), HttpStatus.OK);
    }

    @GetMapping("average/{movieId}")
    public ResponseEntity<Double> getTotalAvg(@PathVariable Float movieId) {
        return new ResponseEntity<>(movieService.totalAverageRate(movieId), HttpStatus.OK);
    }

    @PostMapping("rateMovie/{movieId}")
    public ResponseEntity<User> rateMovie(@RequestBody AddMovieRate addMovieRate, @PathVariable("movieId") Float movieId) {
        return new ResponseEntity<>(movieService.addRateToMovie(addMovieRate, movieId), HttpStatus.OK);
    }

    @PatchMapping("update/{movieId}/rate")
    public ResponseEntity<UserMovie> updatePassword(@RequestBody AddMovieRate addMovieRate, @PathVariable("movieId") Float movieId) {
        return new ResponseEntity<>(movieService.updateMovieRate(addMovieRate, movieId), HttpStatus.OK);
    }

    @DeleteMapping("delete/{movieId}")
    public ResponseEntity<String> deleteUser(@PathVariable("movieId") Float movieId) {
        return new ResponseEntity<>(movieService.deleteMovie(movieId), HttpStatus.NO_CONTENT);
    }

}
