package br.com.senior.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.senior.cursomc.domain.Cliente;
import br.com.senior.cursomc.domain.Cliente;
import br.com.senior.cursomc.dto.ClienteDTO;
import br.com.senior.cursomc.dto.ClienteNewDto;
import br.com.senior.cursomc.services.ClienteService;
import br.com.senior.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> getAll(){
		List<Cliente> lista = service.getAll();
		List<ClienteDTO> objConvert = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(objConvert); 
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value="page", defaultValue="0") Integer page, 
			                                           @RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			                                           @RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			                                           @RequestParam(value="direction", defaultValue="ASC")String direction){
		
		Page<Cliente> lista = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> objConvert = lista.map(obj -> new ClienteDTO(obj));
		
		return ResponseEntity.ok().body(objConvert);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Optional<Cliente>> getOne(@PathVariable Integer id) {
		
			Optional<Cliente> obj = service.buscar(id);
			return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> post(@Valid @RequestBody ClienteNewDto objDto){
		Cliente obj = service.fromDTO(objDto);
		obj = service.save(obj);
		//busca a chamada do método (categorias/) e resgata o id criado 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> put(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDto){
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.put(obj);
		
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
			service.delete(id);
			return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@RequestParam(value="value") String email){
		Cliente obj = service.findByEmail(email);
		return ResponseEntity.ok(obj);
	}
	
	
	
	

}
