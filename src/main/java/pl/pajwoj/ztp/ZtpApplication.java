package pl.pajwoj.ztp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import pl.pajwoj.ztp.services.BookService;
import pl.pajwoj.ztp.services.UserService;

@SpringBootApplication
public class ZtpApplication {
	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(ZtpApplication.class, args);

		UserService s = app.getBean(UserService.class);
		BookService b = app.getBean(BookService.class);

		s.register("EMAIL@WP.PL", "PASS");
		b.add("9780156030434", "The Mysterious Flame Of Queen Loana", "Umberto Eco");
	}

}
