package br.com.senior.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.ItemPedido;
import br.com.senior.cursomc.repositories.CategoriaRepository;
import br.com.senior.cursomc.repositories.ItensPedidoRepository;
import br.com.senior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ItemPedidoService {

	@Autowired
	private ItensPedidoRepository repo;
	
	public Optional<ItemPedido> buscar(Integer id) {
		Optional<ItemPedido> obj = repo.findById(id);
		
		if(obj.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: "+ id + ", Tipo: "+ ItemPedido.class.getName());
		}
		return obj;
	}

	public List<ItemPedido> getAll() {
		
		List<ItemPedido> obj = repo.findAll();
		return obj;
	}
	
	public void saveAll(List<ItemPedido> obj) {
		repo.saveAll(obj);
	}
	
}
