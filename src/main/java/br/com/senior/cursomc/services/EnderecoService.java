package br.com.senior.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senior.cursomc.domain.Endereco;
import br.com.senior.cursomc.repositories.EnderecoRepository;
import br.com.senior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repo;

	public Optional<Endereco> buscar(Integer id) {
		Optional<Endereco> obj = repo.findById(id);

		if (obj.isEmpty()) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Endereco.class.getName());
		}
		return obj;
	}

	public List<Endereco> getAll() {

		List<Endereco> obj = repo.findAll();
		return obj;
	}

	public void saveAll(List<Endereco> obj) {
		repo.saveAll(obj);
	}
}
