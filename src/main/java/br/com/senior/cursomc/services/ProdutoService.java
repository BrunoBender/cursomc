package br.com.senior.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.Pedido;
import br.com.senior.cursomc.domain.Produto;
import br.com.senior.cursomc.repositories.CategoriaRepository;
import br.com.senior.cursomc.repositories.ProdutoRepository;
import br.com.senior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	@Autowired
	private CategoriaRepository repoCat;
	
	public Optional<Produto> buscar(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		
		if(obj.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: "+ id + ", Tipo: "+ Produto.class.getName());
		}
		return obj;
	}

	public List<Produto> getAll() {
		
		List<Produto> obj = repo.findAll();
		return obj;
	}
	
	public void saveAll(List<Produto> obj) {
		repo.saveAll(obj);
	}
	
	public Page<Produto> search(String nome, List<Integer> categoriaIds, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = repoCat.findAllById(categoriaIds);
		
		return repo.search(nome, categorias, pageRequest);
		
	}
	
}
