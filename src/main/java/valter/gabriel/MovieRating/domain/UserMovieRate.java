package valter.gabriel.MovieRating.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserMovieRate {
    @Id
    private Float id;
    private String title;
    private String original_language;
    private String release_date;
    private String backdrop_path;


    private Integer rating;

}
