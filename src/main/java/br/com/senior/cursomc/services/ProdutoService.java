package br.com.senior.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.Produto;
import br.com.senior.cursomc.repositories.CategoriaRepository;
import br.com.senior.cursomc.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	public Optional<Produto> buscar(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj;
	}

	public List<Produto> getAll() {
		
		List<Produto> obj = repo.findAll();
		return obj;
	}
	
	public void saveAll(List<Produto> obj) {
		repo.saveAll(obj);
	}
	
}
