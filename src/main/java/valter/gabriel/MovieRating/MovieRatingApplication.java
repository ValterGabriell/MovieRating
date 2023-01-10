package valter.gabriel.MovieRating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import valter.gabriel.MovieRating.domain.Role;

@SpringBootApplication
public class MovieRatingApplication {
	public static void main(String[] args) {
		SpringApplication.run(MovieRatingApplication.class, args);
	}



	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


}
