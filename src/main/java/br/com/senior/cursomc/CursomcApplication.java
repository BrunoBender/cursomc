package br.com.senior.cursomc;

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
import br.com.senior.cursomc.domain.Produto;
import br.com.senior.cursomc.domain.enums.TipoCliente;
import br.com.senior.cursomc.repositories.CategoriaRepository;
import br.com.senior.cursomc.repositories.CidadeRepository;
import br.com.senior.cursomc.services.CategoriaService;
import br.com.senior.cursomc.services.CidadeService;
import br.com.senior.cursomc.services.ClienteService;
import br.com.senior.cursomc.services.EnderecoService;
import br.com.senior.cursomc.services.EstadoService;
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
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		serviceCat.saveAll(Arrays.asList(cat1, cat2));
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
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("234234234", "23432434"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 300", "Jardim", "02394093", cli1, cid1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "23494093", cli1, cid2);

		serviceCli.saveAll(Arrays.asList(cli1));
		serviceEnd.saveAll(Arrays.asList(e1, e2));
		
	}
	
	

}

