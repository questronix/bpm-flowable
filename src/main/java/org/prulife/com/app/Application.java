package org.prulife.com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
public class Application {
    private static ConfigurableApplicationContext appContext;

    public static void main(String[] args) {
        appContext = SpringApplication.run(Application.class, args);
    }

}
