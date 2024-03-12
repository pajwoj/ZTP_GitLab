package pl.pajwoj.ztp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import pl.pajwoj.ztp.services.UserService;

@SpringBootApplication
public class ZtpApplication {
	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(ZtpApplication.class, args);

		UserService s = app.getBean(UserService.class);
	}

}
