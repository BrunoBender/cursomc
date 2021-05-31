package br.com.senior.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.senior.cursomc.services.DbService;

@Configuration
//todos os beans só seram executados se o usuário cadastrado for o dev
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DbService dbService;

//	pega o valor armazenado no application-dev.properties e salva em uma variável, caso for "create" será
//	inserido todos os dados no banco ao executar o projeto
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {

		if(!"create".equals(strategy)){
			return false;
		}
		dbService.instantiateTestDatabase();
		return true;
	}
	
}
