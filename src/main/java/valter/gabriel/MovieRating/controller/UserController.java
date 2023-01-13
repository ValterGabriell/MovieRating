package valter.gabriel.MovieRating.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.MovieRating.domain.User;
import valter.gabriel.MovieRating.domain.UserMovie;
import valter.gabriel.MovieRating.domain.dto.SaveUserRequest;
import valter.gabriel.MovieRating.service.UsersService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;


    @GetMapping("get/{username}")
    public ResponseEntity<User> getUsers(@PathVariable("username") String username) {
        return new ResponseEntity<>(usersService.getUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping("get/movies")
    public ResponseEntity<List<UserMovie>> getMovies() {
        return new ResponseEntity<>(usersService.getAllMovieThatUserAreRated(), HttpStatus.OK);
    }

    @PostMapping("save")
    public ResponseEntity<User> createSimpleUser(@RequestBody SaveUserRequest userRequest) {
        return new ResponseEntity<>(usersService.saveUser(userRequest), HttpStatus.CREATED);
    }

    @PatchMapping("update/username")
    public ResponseEntity<User> updateUsername(@RequestBody SaveUserRequest userRequest) {
        return new ResponseEntity<>(usersService.updateUsername(userRequest), HttpStatus.OK);
    }

    @PatchMapping("update/fullname")
    public ResponseEntity<User> updateFullname(@RequestBody SaveUserRequest userRequest) {
        return new ResponseEntity<>(usersService.updateFullname(userRequest), HttpStatus.OK);
    }

    @PatchMapping("update/email")
    public ResponseEntity<User> updateEmail(@RequestBody SaveUserRequest userRequest) {
        return new ResponseEntity<>(usersService.updateEmail(userRequest), HttpStatus.OK);
    }

    @PatchMapping("update/password")
    public ResponseEntity<User> updatePassword(@RequestBody SaveUserRequest userRequest) {
        return new ResponseEntity<>(usersService.updatePassword(userRequest), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteUser() {
        return new ResponseEntity<>(usersService.deleteUser(), HttpStatus.NO_CONTENT);
    }
}
