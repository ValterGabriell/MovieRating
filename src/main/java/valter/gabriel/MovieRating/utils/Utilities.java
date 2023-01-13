package valter.gabriel.MovieRating.utils;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import valter.gabriel.MovieRating.domain.User;
import valter.gabriel.MovieRating.handle.ApiRequestException;
import valter.gabriel.MovieRating.repo.UsersRepo;

public class Utilities {
    public static User getUserByToken(UsersRepo usersRepo) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String name;
        if (principal instanceof UserDetails) {
            name = ((UserDetails) principal).getUsername();
        } else {
            name = principal.toString();
        }

        return usersRepo.findByUsername(name).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
