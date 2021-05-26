package br.com.senior.cursomc.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.Pedido;
import br.com.senior.cursomc.services.CategoriaService;
import br.com.senior.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
//	@RequestMapping(method=RequestMethod.GET)
//	public ResponseEntity<?> getAll(){
//		List<Categoria> obj = service.getAll();
//		
//		return ResponseEntity.ok().body(obj);
//	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getOne(@PathVariable Integer id) {
		
			Optional<Pedido> obj = service.buscar(id);
			return ResponseEntity.ok().body(obj);
		
	}
}
