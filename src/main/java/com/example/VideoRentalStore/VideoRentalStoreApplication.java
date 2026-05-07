package com.example.VideoRentalStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VideoRentalStoreApplication {

    public static void main(String[] args) {
       ApplicationContext context =  SpringApplication.run(VideoRentalStoreApplication.class, args);
    }

}
