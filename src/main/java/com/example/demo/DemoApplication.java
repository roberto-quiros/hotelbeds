package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.config.HackingProperties;
import com.example.hackerdetector.HackerDetectorImpl;

@SpringBootApplication
@EnableConfigurationProperties(HackingProperties.class)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
		System.out.println( "Hello Hotelbeds!" );
		HackerDetectorImpl hackerDetectorImpl = new HackerDetectorImpl();

        String result = hackerDetectorImpl.parseLine("80.238.9.179,133612947,SIGNIN_SUCCESS,Will.Smith");
        System.out.println(result);
		
	}

}
