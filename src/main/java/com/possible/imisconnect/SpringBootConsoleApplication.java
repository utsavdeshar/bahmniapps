package com.possible.imisconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.possible.imisconnect")
public class SpringBootConsoleApplication  {

    /*@Autowired
    DataSource dataSource;*/

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootConsoleApplication.class, args);
        System.out.println("Hello app");
    }

    
}