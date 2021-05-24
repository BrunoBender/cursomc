package br.com.senior.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.repositories.CategoriaRepository;
import br.com.senior.cursomc.services.CategoriaService;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	@Autowired
	CategoriaService service;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		service.saveAll(Arrays.asList(cat1, cat2));
		
	}
	
	

}

