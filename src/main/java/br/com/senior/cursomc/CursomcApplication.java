package br.com.senior.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.Cidade;
import br.com.senior.cursomc.domain.Cliente;
import br.com.senior.cursomc.domain.Endereco;
import br.com.senior.cursomc.domain.Estado;
import br.com.senior.cursomc.domain.ItemPedido;
import br.com.senior.cursomc.domain.Pagamento;
import br.com.senior.cursomc.domain.PagamentoComBoleto;
import br.com.senior.cursomc.domain.PagamentoComCartao;
import br.com.senior.cursomc.domain.Pedido;
import br.com.senior.cursomc.domain.Produto;
import br.com.senior.cursomc.domain.enums.EstadoPagamento;
import br.com.senior.cursomc.domain.enums.TipoCliente;
import br.com.senior.cursomc.repositories.CategoriaRepository;
import br.com.senior.cursomc.repositories.CidadeRepository;
import br.com.senior.cursomc.services.CategoriaService;
import br.com.senior.cursomc.services.CidadeService;
import br.com.senior.cursomc.services.ClienteService;
import br.com.senior.cursomc.services.EnderecoService;
import br.com.senior.cursomc.services.EstadoService;
import br.com.senior.cursomc.services.ItemPedidoService;
import br.com.senior.cursomc.services.PagamentoService;
import br.com.senior.cursomc.services.PedidoService;
import br.com.senior.cursomc.services.ProdutoService;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	@Autowired
	CategoriaService serviceCat;
	@Autowired
	ProdutoService serviceProd;
	@Autowired
	CidadeService serviceCid;
	@Autowired
	EstadoService serviceEst;
	@Autowired
	EnderecoService serviceEnd;
	@Autowired
	ClienteService serviceCli;
	@Autowired
	PedidoService servicePed;
	@Autowired
	PagamentoService servicepagto;
	@Autowired
	ItemPedidoService serviceItemP;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Decoração");
		Categoria cat4 = new Categoria(null, "Cama mesa e banho");
		Categoria cat5 = new Categoria(null, "HortFrut");
		Categoria cat6 = new Categoria(null, "Carnes");
		Categoria cat7 = new Categoria(null, "Laticineos");
		Categoria cat8 = new Categoria(null, "Queijos");
		
		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		serviceCat.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8));
		serviceProd.saveAll(Arrays.asList(prod1, prod2, prod3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		
		serviceEst.saveAll(Arrays.asList(est1, est2));
		serviceCid.saveAll(Arrays.asList(cid1, cid2, cid3));
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "2342423434", "maria@gmail.com", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("234234234", "23432434"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 300", "Jardim", "02394093", cli1, cid1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "23494093", cli1, cid2);

		serviceCli.saveAll(Arrays.asList(cli1));
		serviceEnd.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		
		ped1.setPagamento(pagto1);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		servicePed.saveAll(Arrays.asList(ped1, ped2));
		servicepagto.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, prod1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, prod3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, prod2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		prod1.getItens().addAll(Arrays.asList(ip1));
		prod2.getItens().addAll(Arrays.asList(ip3));
		prod3.getItens().addAll(Arrays.asList(ip2));
		
		serviceItemP.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		
	}
	
	

}

