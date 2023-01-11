package valter.gabriel.MovieRating.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String fullname;
    private String username;
    private String email;
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<Role>();

    @OneToMany(targetEntity = UserMovieRate.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_fk", referencedColumnName = "userId")
    private List<UserMovieRate> userMovieRates;

}
