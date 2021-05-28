package br.com.senior.cursomc.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.cursomc.domain.Produto;
import br.com.senior.cursomc.domain.Pedido;
import br.com.senior.cursomc.domain.Produto;
import br.com.senior.cursomc.dto.ProdutoDTO;
import br.com.senior.cursomc.resources.utils.URL;
import br.com.senior.cursomc.services.ProdutoService;
import br.com.senior.cursomc.services.PedidoService;
import br.com.senior.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
//	@RequestMapping(method=RequestMethod.GET)
//	public ResponseEntity<?> getAll(){
//		List<Produto> obj = service.getAll();
//		
//		return ResponseEntity.ok().body(obj);
//	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getOne(@PathVariable Integer id) {
		
			Optional<Produto> obj = service.buscar(id);
			return ResponseEntity.ok().body(obj);
		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") String categorias, 
			@RequestParam(value="page", defaultValue="0")Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")String direction
		){
		
		Page<Produto> lista = service.search(URL.decodeParam(nome), URL.decodeIntList(categorias) ,page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> objConvert = lista.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(objConvert);
	}
}
