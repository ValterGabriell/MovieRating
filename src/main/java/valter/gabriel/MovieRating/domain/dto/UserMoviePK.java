package valter.gabriel.MovieRating.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMoviePK implements Serializable {
    private Long userId;
    private Float movieId;
}
