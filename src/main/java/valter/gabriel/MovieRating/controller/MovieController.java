package valter.gabriel.MovieRating.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import valter.gabriel.MovieRating.domain.Movie;
import valter.gabriel.MovieRating.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie/")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }
}
