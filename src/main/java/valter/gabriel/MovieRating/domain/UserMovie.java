package valter.gabriel.MovieRating.domain;


import lombok.Data;
import valter.gabriel.MovieRating.domain.dto.UserMoviePK;

import javax.persistence.*;

@Entity
@Data
public class UserMovie {


    @EmbeddedId
    private UserMoviePK userMoviePK;

    private String title;
    private String original_language;
    private String release_date;
    private String backdrop_path;


    private Integer rating;

}
