package valter.gabriel.MovieRating.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import valter.gabriel.MovieRating.domain.Movie;
import valter.gabriel.MovieRating.domain.Role;
import valter.gabriel.MovieRating.domain.User;
import valter.gabriel.MovieRating.domain.UserMovieRate;
import valter.gabriel.MovieRating.domain.dto.AddMovieRate;
import valter.gabriel.MovieRating.domain.dto.SaveUserRequest;
import valter.gabriel.MovieRating.repo.MovieRepo;
import valter.gabriel.MovieRating.repo.UserMovieRepo;
import valter.gabriel.MovieRating.repo.UsersRepo;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepo usersRepo;
    private final MovieRepo movieRepo;
    private final UserMovieRepo userMovieRepo;
    private final PasswordEncoder passwordEncoder;

    public User saveAdmin(SaveUserRequest user) {
        ModelMapper mapper = new ModelMapper();
        User myUser = mapper.map(user, User.class);

        myUser.setPassword(passwordEncoder.encode(user.getPassword()));
        myUser.getRoles().add(Role.ROLE_ADMIN);
        myUser.getRoles().add(Role.ROLE_USER);

        return usersRepo.save(myUser);
    }

    public User saveUser(SaveUserRequest user) {
        ModelMapper mapper = new ModelMapper();
        User myUser = mapper.map(user, User.class);

        myUser.setPassword(passwordEncoder.encode(user.getPassword()));
        myUser.getRoles().add(Role.ROLE_USER);

        return usersRepo.save(myUser);
    }

    public User addRateToMovie(AddMovieRate addMovieRate){
        Movie movie = movieRepo.findById(addMovieRate.getMovieId()).orElseThrow(()-> new EntityNotFoundException("Movie not found"));
        User user = usersRepo.findById(addMovieRate.getUserId()).orElseThrow(()-> new EntityNotFoundException("Movie not found"));

        ModelMapper mapper = new ModelMapper();
        UserMovieRate movieRate = mapper.map(movie, UserMovieRate.class);

        movieRate.setRating(addMovieRate.getRate());


        user.getUserMovieRates().add(movieRate);

        usersRepo.save(user);
        userMovieRepo.save(movieRate);
        return user;
    }


    public User getUserByUsername(String username) {
        return usersRepo.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepo.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
