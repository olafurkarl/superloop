package com.superloop.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @PostConstruct
    public void init(){
        // We want all dates to be stored and validated in UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
