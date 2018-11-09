package org.prulife.com;

import org.prulife.com.services.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner init(final UsersService myService) {

        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
//                myService.createDemoUsers();
            }
        };
    }

}
