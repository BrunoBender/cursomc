package br.com.senior.cursomc.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.Pedido;
import br.com.senior.cursomc.dto.CategoriaDTO;
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
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> post(@Valid @RequestBody Pedido obj){
		obj = service.save(obj);
		//busca a chamada do método (categorias/) e resgata o id criado 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> findPage(@RequestParam(value="page", defaultValue="0") Integer page,
													   @RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage,
													   @RequestParam(value="orderBy", defaultValue="instante")String orderBy,
													   @RequestParam(value="direction", defaultValue="DESC")String direction){

		Page<Pedido> lista = service.findPage(page, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(lista);
	}
}
