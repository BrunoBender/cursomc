package br.com.senior.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Optional<Categoria> buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj;
	}

	public List<Categoria> getAll() {
		
		List<Categoria> obj = repo.findAll();
		return obj;
	}
	
}
