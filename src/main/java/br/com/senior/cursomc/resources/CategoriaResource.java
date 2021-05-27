package br.com.senior.cursomc.resources;

import java.net.URI;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.dto.CategoriaDTO;
import br.com.senior.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> getAll(){
		List<Categoria> lista = service.getAll();
		List<CategoriaDTO> objConvert = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(objConvert); 
	}
	
	@RequestMapping(value="page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(value="page", defaultValue="0") Integer page, 
			                                           @RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			                                           @RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			                                           @RequestParam(value="direction", defaultValue="ASC")String direction){
		
		Page<Categoria> lista = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> objConvert = lista.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(objConvert);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Optional<Categoria>> getOne(@PathVariable Integer id) {
		
			Optional<Categoria> obj = service.buscar(id);
			return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> post(@Valid @RequestBody CategoriaDTO categoriaDto){
		Categoria categoria = service.fromDTO(categoriaDto);
		categoria = service.save(categoria);
		//busca a chamada do método (categorias/) e resgata o id criado 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> put(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO objDto){
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.put(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
			service.delete(id);
			return ResponseEntity.noContent().build();
	}
}
