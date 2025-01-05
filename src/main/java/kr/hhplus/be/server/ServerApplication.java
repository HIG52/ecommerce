package kr.hhplus.be.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {

		ApplicationContext test = SpringApplication.run(ServerApplication.class, args);

		if (test.containsBean("balanceJpaRepository")) {
			System.out.println("balanceJpaRepository bean is created");
		} else {
			System.out.println("balanceJpaRepository bean is not created");
		}
	}

}
