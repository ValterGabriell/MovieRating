package valter.gabriel.MovieRating.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserRequest {

    private String fullname;
    private String username;
    private String email;
    private String password;
    private String token;
}
