package br.com.senior.cursomc.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.cursomc.domain.Endereco;
import br.com.senior.cursomc.services.EnderecoService;

@RestController
@RequestMapping(value="/enderecos")
public class EnderecoResource {
	
	@Autowired
	EnderecoService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getOne(@PathVariable Integer id){
		Optional<Endereco> obj = service.buscar(id);
		return ResponseEntity.ok(obj);
	}

}
