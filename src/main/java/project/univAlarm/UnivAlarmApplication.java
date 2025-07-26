package project.univAlarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import project.univAlarm.config.UnivConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(UnivConfigProperties.class)
public class UnivAlarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnivAlarmApplication.class, args);
	}

}
