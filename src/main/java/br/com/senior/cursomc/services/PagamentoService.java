package br.com.senior.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.Pagamento;
import br.com.senior.cursomc.domain.Pedido;
import br.com.senior.cursomc.domain.Produto;
import br.com.senior.cursomc.repositories.CategoriaRepository;
import br.com.senior.cursomc.repositories.PagamentoRepository;
import br.com.senior.cursomc.repositories.ProdutoRepository;
import br.com.senior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository repo;

	public Optional<Pagamento> buscar(Integer id) {
		Optional<Pagamento> obj = repo.findById(id);

		if (obj.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pagamento.class.getName());
		}
		return obj;
	}

	public List<Pagamento> getAll() {

		List<Pagamento> obj = repo.findAll();
		return obj;
	}

	public void saveAll(List<Pagamento> obj) {
		repo.saveAll(obj);
	}

}
