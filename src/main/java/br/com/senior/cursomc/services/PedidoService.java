package br.com.senior.cursomc.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.ItemPedido;
import br.com.senior.cursomc.domain.PagamentoComBoleto;
import br.com.senior.cursomc.domain.Pedido;
import br.com.senior.cursomc.domain.enums.EstadoPagamento;
import br.com.senior.cursomc.repositories.CategoriaRepository;
import br.com.senior.cursomc.repositories.PagamentoRepository;
import br.com.senior.cursomc.repositories.PedidoRepository;
import br.com.senior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository PagtoRepo;
	@Autowired
	ProdutoService prodService;
	@Autowired
	ItemPedidoService itemPService;
	public Optional<Pedido> buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		
		if(obj.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: "+ id + ", Tipo: "+ Pedido.class.getName());
		}
		return obj;
	}

	public List<Pedido> getAll() {
		
		List<Pedido> obj = repo.findAll();
		return obj;
	}
	
	public void saveAll(List<Pedido> obj) {
		repo.saveAll(obj);
	}
	
	@Transactional
	public Pedido save(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		PagtoRepo.save(obj.getPagamento());
		for(ItemPedido item : obj.getItens()) {
			item.setDesconto(0.0);
			item.setPreco(prodService.buscar(item.getProduto().getId()).get().getPreco());
			item.setPedido(obj); 
		}
		itemPService.saveAll(new ArrayList<ItemPedido>(obj.getItens()));
		
		return obj;
	}
	
}
