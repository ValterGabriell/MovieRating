package valter.gabriel.MovieRating.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import valter.gabriel.MovieRating.domain.Role;
import valter.gabriel.MovieRating.domain.User;
import valter.gabriel.MovieRating.domain.dto.SaveUserRequest;
import valter.gabriel.MovieRating.repo.UsersRepo;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;

    public User saveAdmin(SaveUserRequest user) {
        ModelMapper mapper = new ModelMapper();
        User myUser = mapper.map(user, User.class);

        myUser.setPassword(passwordEncoder.encode(user.getPassword()));
        myUser.getRoles().add(Role.ROLE_ADMIN);
        myUser.getRoles().add(Role.ROLE_USER);

        return usersRepo.save(myUser);
    }

    public User saveSimpleUser(SaveUserRequest user) {
        ModelMapper mapper = new ModelMapper();
        User myUser = mapper.map(user, User.class);

        myUser.setPassword(passwordEncoder.encode(user.getPassword()));
        myUser.getRoles().add(Role.ROLE_USER);

        return usersRepo.save(myUser);
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
