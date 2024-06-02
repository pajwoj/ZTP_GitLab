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

		//add mock data
		s.register("EMAIL@WP.PL", "PASS");
		b.add("9780156030434", "The Mysterious Flame Of Queen Loana", "Umberto Eco");
		b.add("1", "The Mysterious Flame Of Queen Loana", "Umberto Eco");
		b.add("2", "b2", "Umberto Eco");
		b.add("3", "b3", "Umberto Eco");
		b.add("4", "b4", "Umberto Eco");
		b.add("5", "b5", "Umberto Eco");
		b.add("6", "b6", "Umberto Eco");
	}

}
