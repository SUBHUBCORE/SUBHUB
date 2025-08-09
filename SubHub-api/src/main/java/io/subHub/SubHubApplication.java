package io.subHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * SubHub-api
 *
 * @author By
 */
@SpringBootApplication
@EnableScheduling
public class SubHubApplication extends SpringBootServletInitializer{


	public static void main(String[] args) {
		SpringApplication.run(SubHubApplication.class, args);
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SubHubApplication.class);
	}
}
