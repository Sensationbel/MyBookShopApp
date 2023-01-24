package com.example.MyBookShopAPP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MyBookShopAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBookShopAppApplication.class, args);

	}

}
