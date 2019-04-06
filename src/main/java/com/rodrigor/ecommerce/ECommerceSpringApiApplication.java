package com.rodrigor.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rodrigor.ecommerce.services.S3Service;

@SpringBootApplication
public class ECommerceSpringApiApplication implements CommandLineRunner{
	
	@Autowired
	private S3Service s3Service;
	
	public static void main(String[] args) {
		SpringApplication.run(ECommerceSpringApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("E:\\Estudo\\SpringBoot\\material-curso-springboot\\teste.jpg");
	}

}
