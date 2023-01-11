package valter.gabriel.MovieRating.domain.dto;


import lombok.Data;

@Data
public class AddMovieRate {

    private Long userId;
    private Float movieId;
    private Integer rate;


}
