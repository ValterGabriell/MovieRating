package valter.gabriel.MovieRating.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.MovieRating.domain.User;
import valter.gabriel.MovieRating.domain.dto.SaveUserRequest;
import valter.gabriel.MovieRating.service.MovieService;
import valter.gabriel.MovieRating.service.UsersService;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
public class AdminController {
    private final UsersService usersService;
    private final MovieService movieService;


    @PostMapping("save")
    public ResponseEntity<User> createAdmin(@RequestBody SaveUserRequest users) {
        return new ResponseEntity<>(usersService.saveAdmin(users), HttpStatus.CREATED);
    }

    @GetMapping("getMovies")
    public ResponseEntity<?> getMoviesFromApi() {
        movieService.getMoviesDataFromApi();
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
