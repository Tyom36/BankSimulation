package rogue.tyom.sub.securityapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BankSimApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSimApplication.class, args);
	}

}
