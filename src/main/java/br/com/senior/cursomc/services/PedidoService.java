package br.com.senior.cursomc.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.com.senior.cursomc.domain.*;
import br.com.senior.cursomc.security.UserSS;
import br.com.senior.cursomc.services.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private ProdutoService prodService;
	@Autowired
	private ItemPedidoService itemPService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private EmailService emailService;

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
		obj.setCliente(clienteService.buscar(obj.getCliente().getId()).get());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		PagtoRepo.save(obj.getPagamento());
		for(ItemPedido item : obj.getItens()) {
			item.setProduto(prodService.buscar(item.getProduto().getId()).get());
			item.setDesconto(0.0);
			item.setPreco(prodService.buscar(item.getProduto().getId()).get().getPreco());
			item.setPedido(obj); 
		}
		itemPService.saveAll(new ArrayList<ItemPedido>(obj.getItens()));

		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}

	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		//Realiza a autenticação
		UserSS user = UserService.authenticated();
		if(user == null){
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		Optional<Cliente> cliente = clienteService.buscar(user.getId());

		return repo.findByCliente(cliente.get(), pageRequest);
	}


	 
}
