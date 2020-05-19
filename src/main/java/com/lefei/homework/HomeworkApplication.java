package com.lefei.homework;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class HomeworkApplication implements CommandLineRunner {
	private final Application fpApplication;

	public HomeworkApplication(Application fpApplication) {
		this.fpApplication = fpApplication;
	}
	public static void main(String[] args) {
		SpringApplication.run(HomeworkApplication.class, args);
	}

	@Override
	public void run(String... args){
		BigDecimal listingPrice = new BigDecimal(90);

		System.out.println(fpApplication.run(listingPrice));
	}
}
