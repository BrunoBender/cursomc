package br.com.senior.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.repositories.CategoriaRepository;
import br.com.senior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Optional<Categoria> buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		
		if(obj.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: "+ id + ", Tipo: "+ Categoria.class.getName());
		}
		return obj;
	}

	public List<Categoria> getAll() {
		
		List<Categoria> obj = repo.findAll();
		return obj;
	}
	
	public void saveAll(List<Categoria> obj) {
		repo.saveAll(obj);
	}

	public Categoria save(Categoria obj) {	
		obj.setId(null);
		return repo.save(obj);
	}
	
}
