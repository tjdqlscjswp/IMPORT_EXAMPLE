package com.kosta.KOSTA_3_final;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.kosta"})
public class Kosta3FinalApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Kosta3FinalApplication.class, args);



			}
	}

