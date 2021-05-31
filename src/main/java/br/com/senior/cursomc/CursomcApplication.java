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
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		
		
		
	}
	
	

}

