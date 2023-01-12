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

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepo usersRepo;
    private final MovieRepo movieRepo;
    private final PasswordEncoder passwordEncoder;

    public User saveAdmin(SaveUserRequest user) {
        ModelMapper mapper = new ModelMapper();
        User admin = mapper.map(user, User.class);

        admin.setPassword(passwordEncoder.encode(user.getPassword()));
        admin.getRoles().add(Role.ROLE_ADMIN);
        admin.getRoles().add(Role.ROLE_USER);

        return usersRepo.save(admin);
    }

    public User saveUser(SaveUserRequest user) {
        ModelMapper mapper = new ModelMapper();
        User simpleUser = mapper.map(user, User.class);

        simpleUser.setPassword(passwordEncoder.encode(user.getPassword()));
        simpleUser.getRoles().add(Role.ROLE_USER);

        return usersRepo.save(simpleUser);
    }

    public User addRateToMovie(AddMovieRate addMovieRate, Float movieId) {
        ModelMapper mapper = new ModelMapper();

        User user = usersRepo.findByUsername(getUsernameByToken()).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User not found"));


        //recebendo filme e salvando ele na entidade de UserMovie
        Movie movie = movieRepo.findById(movieId).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Movie not found"));
        UserMovie userMovie = mapper.map(movie, UserMovie.class);


        //criando chave estrangeira e setando no objeto userMovie
        UserMoviePK userMoviePK = new UserMoviePK(user.getUserId(), movieId);
        userMovie.setUserMoviePK(userMoviePK);
        userMovie.setRating(addMovieRate.getRate());

        user.getUserMovies().add(userMovie);

        usersRepo.save(user);
        return user;
    }


    public User getUserByUsername(String username) {
        return usersRepo.findByUsername(username).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User not found"));
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

    private String getUsernameByToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String name;
        if (principal instanceof UserDetails) {
            name = ((UserDetails) principal).getUsername();
        } else {
            name = principal.toString();
        }

        return name;
    }
}
