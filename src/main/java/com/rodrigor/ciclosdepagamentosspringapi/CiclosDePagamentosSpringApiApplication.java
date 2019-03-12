package com.rodrigor.ciclosdepagamentosspringapi;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rodrigor.ciclosdepagamentosspringapi.domain.Categoria;
import com.rodrigor.ciclosdepagamentosspringapi.repositories.CategoriaRepository;

@SpringBootApplication
public class CiclosDePagamentosSpringApiApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CiclosDePagamentosSpringApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cagetoria1 = new Categoria(null, "Informática");
		Categoria cagetoria2 = new Categoria(null, "Escritório");
		
		categoriaRepository.saveAll(Arrays.asList(cagetoria1, cagetoria2));
		
	}

}
