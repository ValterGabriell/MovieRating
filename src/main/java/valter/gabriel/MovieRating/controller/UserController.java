package valter.gabriel.MovieRating.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.MovieRating.domain.User;
import valter.gabriel.MovieRating.domain.dto.AddMovieRate;
import valter.gabriel.MovieRating.domain.dto.SaveUserRequest;
import valter.gabriel.MovieRating.service.MovieService;
import valter.gabriel.MovieRating.service.UsersService;


@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;


    @GetMapping("get/{username}")
    public ResponseEntity<User> getUsers(@PathVariable("username") String username) {
        return new ResponseEntity<>(usersService.getUserByUsername(username), HttpStatus.OK);
    }


    @PostMapping("save")
    public ResponseEntity<User> createSimpleUser(@RequestBody SaveUserRequest users) {
        return new ResponseEntity<>(usersService.saveUser(users), HttpStatus.CREATED);
    }


    @PostMapping("rate-movie/{movieId}")
    public ResponseEntity<User> rateMovie(@RequestBody AddMovieRate addMovieRate, @PathVariable("movieId") Float movieId) {
        return new ResponseEntity<>(usersService.addRateToMovie(addMovieRate, movieId), HttpStatus.OK);
    }
}
