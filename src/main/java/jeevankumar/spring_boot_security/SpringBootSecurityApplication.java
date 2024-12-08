package jeevankumar.spring_boot_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringBootSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityApplication.class, args);
	}

}
