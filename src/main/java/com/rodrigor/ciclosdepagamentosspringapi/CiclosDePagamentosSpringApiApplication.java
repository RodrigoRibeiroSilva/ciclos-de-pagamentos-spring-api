package com.rodrigor.ciclosdepagamentosspringapi;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rodrigor.ciclosdepagamentosspringapi.domain.Categoria;
import com.rodrigor.ciclosdepagamentosspringapi.domain.Cidade;
import com.rodrigor.ciclosdepagamentosspringapi.domain.Cliente;
import com.rodrigor.ciclosdepagamentosspringapi.domain.Endereco;
import com.rodrigor.ciclosdepagamentosspringapi.domain.Estado;
import com.rodrigor.ciclosdepagamentosspringapi.domain.Produto;
import com.rodrigor.ciclosdepagamentosspringapi.domain.enums.TipoCliente;
import com.rodrigor.ciclosdepagamentosspringapi.repositories.CategoriaRepository;
import com.rodrigor.ciclosdepagamentosspringapi.repositories.CidadeRepository;
import com.rodrigor.ciclosdepagamentosspringapi.repositories.ClienteRepository;
import com.rodrigor.ciclosdepagamentosspringapi.repositories.EnderecoRepository;
import com.rodrigor.ciclosdepagamentosspringapi.repositories.EstadoRepository;
import com.rodrigor.ciclosdepagamentosspringapi.repositories.ProdutoRepository;

@SpringBootApplication
public class CiclosDePagamentosSpringApiApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CiclosDePagamentosSpringApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		
		
		Produto produto1 = new Produto(null, "Computador", 5000.00);
		Produto produto2 = new Produto(null, "Impressora", 800.00);
		Produto produto3 = new Produto(null, "Mouse", 80.00);
		
		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		categoria2.getProdutos().addAll(Arrays.asList(produto2));
		
		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
		produto3.getCategorias().addAll(Arrays.asList(categoria1));
		
		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
		produtoRepository.saveAll(Arrays.asList(produto1,produto2,produto3));
		
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");
		
		Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
		Cidade cidade2 = new Cidade(null, "Uberlândia", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		
		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
		
		Cliente cliente1 = new Cliente(null , "Maria Silva", "maria@gmailcom", "12345678", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("123456788", "832983298"));
		
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "454675675", cliente1, cidade1);
		Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "323293298", cliente1, cidade2);
		
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		clienteRepository.saveAll(Arrays.asList(cliente1));
		
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
	}

}
