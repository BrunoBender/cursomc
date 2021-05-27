package br.com.senior.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.Cidade;
import br.com.senior.cursomc.domain.Cliente;
import br.com.senior.cursomc.domain.Endereco;
import br.com.senior.cursomc.domain.enums.TipoCliente;
import br.com.senior.cursomc.domain.Cliente;
import br.com.senior.cursomc.dto.ClienteDTO;
import br.com.senior.cursomc.dto.ClienteNewDto;
import br.com.senior.cursomc.repositories.ClienteRepository;
import br.com.senior.cursomc.repositories.EnderecoRepository;
import br.com.senior.cursomc.services.exceptions.DataIntegrityException;
import br.com.senior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	@Autowired
	private EnderecoRepository repoEnd;

	public Optional<Cliente> getOne(Integer id) {
		Optional<Cliente> obj = repo.findById(id);

		if (obj.isEmpty()) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}

	public List<Cliente> getAll() {

		List<Cliente> obj = repo.findAll();
		return obj;
	}

	public void saveAll(List<Cliente> obj) {
		repo.saveAll(obj);
	}
	
	//usado quando se faz inserção de entidades e outras atreladas a ela
	@Transactional
	public Cliente save(Cliente obj) {	
		obj.setId(null);
		repoEnd.saveAll(obj.getEnderecos());
		return repo.save(obj);
	}
	
	public Cliente put(Cliente obj) {
		Optional<Cliente> newObj = buscar(obj.getId());
		return repo.save(UpdateData(newObj.get(), obj));
	}

	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);	
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Cliente porque há entidades atreladas a ele");
		}
		
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(null,obj.getNome(), null, obj.getEmail(), null );
	}
	
	public Cliente fromDTO(ClienteNewDto obj) {
		Cliente cli = new Cliente(null, obj.getNome(), obj.getCpfOuCnpj(), obj.getEmail(), TipoCliente.toEnum(obj.getTipo()));
		Cidade cid = new Cidade(obj.getCidadeId(), null, null);
		Endereco end = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(), obj.getBairro(), obj.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(obj.getTelefone1());
		if(obj.getTelefone2()!=null) {
			cli.getTelefones().add(obj.getTelefone2());
		}
		if(obj.getTelefone3()!=null) {
			cli.getTelefones().add(obj.getTelefone3());
		}
		return cli;
	}
	
	public Optional<Cliente> buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		
		if(obj.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: "+ id + ", Tipo: "+ Cliente.class.getName());
		}
		return obj;
	}

	private Cliente UpdateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		return newObj;
	}
}
