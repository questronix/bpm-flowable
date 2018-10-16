package org.prulife.com;

import org.prulife.com.entities.Users;
import org.prulife.com.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication()
public class Application implements CommandLineRunner {

    @Autowired
    private UsersService usersService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        usersService.create("mhackyu");
        System.out.println("Total number of Users: " + usersService.count());
    }

    //    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/process").allowedOrigins("http://localhost:9000");
//                registry.addMapping("/tasks").allowedOrigins("http://localhost:9000");
//                registry.addMapping("/policy").allowedOrigins("http://localhost:9000");
//            }
//        };
//    }

}
