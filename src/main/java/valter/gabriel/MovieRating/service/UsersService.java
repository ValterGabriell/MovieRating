package valter.gabriel.MovieRating.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import valter.gabriel.MovieRating.domain.*;
import valter.gabriel.MovieRating.domain.dto.AddMovieRate;
import valter.gabriel.MovieRating.domain.dto.SaveUserRequest;
import valter.gabriel.MovieRating.domain.dto.UserMoviePK;
import valter.gabriel.MovieRating.handle.ApiRequestException;
import valter.gabriel.MovieRating.repo.MovieRepo;
import valter.gabriel.MovieRating.repo.UsersRepo;
import valter.gabriel.MovieRating.utils.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepo usersRepo;
    private final MovieRepo movieRepo;
    private final PasswordEncoder passwordEncoder;

    public User saveAdmin(SaveUserRequest user) {
        ModelMapper mapper = new ModelMapper();
        if (usersRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new ApiRequestException(HttpStatus.CONFLICT, "Usuário já existente no banco");
        }

        User admin = mapper.map(user, User.class);

        admin.setPassword(passwordEncoder.encode(user.getPassword()));
        admin.getRoles().add(Role.ROLE_ADMIN);
        admin.getRoles().add(Role.ROLE_USER);
        admin.setToken("Logue para gerar um token e utilizar os endpoints");

        return usersRepo.save(admin);
    }

    public User saveUser(SaveUserRequest user) {
        ModelMapper mapper = new ModelMapper();

        if (usersRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new ApiRequestException(HttpStatus.CONFLICT, "Usuário já existente no banco");
        }


        User simpleUser = mapper.map(user, User.class);

        simpleUser.setPassword(passwordEncoder.encode(user.getPassword()));
        simpleUser.getRoles().add(Role.ROLE_USER);
        simpleUser.setToken("Logue para gerar um token e utilizar os endpoints");

        return usersRepo.save(simpleUser);
    }



    public User getUserByUsername(String username) {
        return usersRepo.findByUsername(username).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User updateUsername(SaveUserRequest userRequest) {
        User userByToken = Utilities.getUserByToken(usersRepo);

        userByToken.setUsername(userRequest.getUsername());
        usersRepo.save(userByToken);
        return userByToken;
    }

    public User updateFullname(SaveUserRequest userRequest) {
        User userByToken = Utilities.getUserByToken(usersRepo);
        userByToken.setFullname(userRequest.getFullname());
        usersRepo.save(userByToken);
        return userByToken;
    }

    public User updateEmail(SaveUserRequest userRequest) {
        User userByToken = Utilities.getUserByToken(usersRepo);
        userByToken.setEmail(userRequest.getEmail());
        usersRepo.save(userByToken);
        return userByToken;
    }

    public User updatePassword(SaveUserRequest userRequest) {
        User userByToken = Utilities.getUserByToken(usersRepo);
        userByToken.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        usersRepo.save(userByToken);
        return userByToken;
    }

    public User updateToken(String token, String username) {
        User user = usersRepo.findByUsername(username).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User not found"));
        user.setToken(token);
        usersRepo.save(user);
        return user;
    }

    public String deleteUser() {
        User userByToken = Utilities.getUserByToken(usersRepo);
        usersRepo.delete(userByToken);
        return "Usuário " + userByToken.getUsername() + " exluído com sucesso! Token " + userByToken.getToken() + " foi invalidado.";
    }

    public List<UserMovie> getAllMovieThatUserAreRated() {
        return Utilities.getUserByToken(usersRepo).getUserMovies();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepo.findByUsername(username).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User not found"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}