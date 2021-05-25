package br.com.senior.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.Cliente;
import br.com.senior.cursomc.repositories.ClienteRepository;
import br.com.senior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

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

}
