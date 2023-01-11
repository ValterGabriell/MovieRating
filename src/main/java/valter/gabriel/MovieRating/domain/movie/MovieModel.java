package valter.gabriel.MovieRating.domain.movie;

import lombok.Data;

import java.util.List;

@Data
public class MovieModel {
    private Integer page;
    private List<Result> results;
    private Integer totalPages;
    private Integer totalResults;



}
