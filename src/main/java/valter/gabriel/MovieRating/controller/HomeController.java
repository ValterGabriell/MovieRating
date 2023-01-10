package valter.gabriel.MovieRating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.MovieRating.domain.Role;
import valter.gabriel.MovieRating.domain.User;
import valter.gabriel.MovieRating.domain.dto.SaveUserRequest;
import valter.gabriel.MovieRating.service.UsersService;

import java.util.List;


@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/user/get/{username}")
    public ResponseEntity<User> getUsers(@PathVariable("username") String username){
        return new ResponseEntity<>(usersService.getUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/user/save/admin")
    public ResponseEntity<User> createAdmin(@RequestBody SaveUserRequest users){
        return new ResponseEntity<>(usersService.saveAdmin(users), HttpStatus.CREATED);
    }

    @PostMapping("/user/save/simple")
    public ResponseEntity<User> createSimpleUser(@RequestBody SaveUserRequest users){
        return new ResponseEntity<>(usersService.saveSimpleUser(users), HttpStatus.CREATED);
    }
}
