package valter.gabriel.MovieRating.domain.movie;

import lombok.Data;

@Data
public class Result {
    private String poster_path;
    private boolean adult;
    private String overview;
    private String release_date;
    private float id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private float popularity;
    private float vote_count;
    private boolean video;
    private float vote_average;
}
