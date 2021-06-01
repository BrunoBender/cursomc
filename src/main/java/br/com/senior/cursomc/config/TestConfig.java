package br.com.senior.cursomc.config;

import java.text.ParseException;

import br.com.senior.cursomc.services.EmailService;
import br.com.senior.cursomc.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.senior.cursomc.services.DbService;

@Configuration
//todos os beans só seram executados se o usuário cadastrado for o test
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DbService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}

	//na classe PedidoService foi definido ua interface EmailService, onde ao exectar ela irá instanciar a subClasse MockEmailService
	//de forma automática
	@Bean
	public EmailService emailService(){
		return new MockEmailService();
	}
	
}
