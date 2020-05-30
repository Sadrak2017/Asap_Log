package projeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = { "controller", "Interface", "message" })
@ComponentScan(basePackages = {"controller", "service", "model"})
@SpringBootApplication
public class AsaplogApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsaplogApplication.class, args);
	}
	

}
