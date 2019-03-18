package com.rodrigor.ecommerce;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rodrigor.ecommerce.domain.Categoria;
import com.rodrigor.ecommerce.domain.Cidade;
import com.rodrigor.ecommerce.domain.Cliente;
import com.rodrigor.ecommerce.domain.Endereco;
import com.rodrigor.ecommerce.domain.Estado;
import com.rodrigor.ecommerce.domain.ItemPedido;
import com.rodrigor.ecommerce.domain.Pagamento;
import com.rodrigor.ecommerce.domain.PagamentoComBoleto;
import com.rodrigor.ecommerce.domain.PagamentoComCartao;
import com.rodrigor.ecommerce.domain.Pedido;
import com.rodrigor.ecommerce.domain.Produto;
import com.rodrigor.ecommerce.domain.enums.EstadoPagamento;
import com.rodrigor.ecommerce.domain.enums.TipoCliente;
import com.rodrigor.ecommerce.repositories.CategoriaRepository;
import com.rodrigor.ecommerce.repositories.CidadeRepository;
import com.rodrigor.ecommerce.repositories.ClienteRepository;
import com.rodrigor.ecommerce.repositories.EnderecoRepository;
import com.rodrigor.ecommerce.repositories.EstadoRepository;
import com.rodrigor.ecommerce.repositories.ItemPedidoRepository;
import com.rodrigor.ecommerce.repositories.PagamentoRepository;
import com.rodrigor.ecommerce.repositories.PedidoRepository;
import com.rodrigor.ecommerce.repositories.ProdutoRepository;

@SpringBootApplication
public class ECommerceSpringApiApplication implements CommandLineRunner{
	
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
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ECommerceSpringApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		Categoria categoria3 = new Categoria(null, "Cama, mesa e banho");
		Categoria categoria4 = new Categoria(null, "Eletrônicos");
		Categoria categoria5 = new Categoria(null, "Jardinagem");
		Categoria categoria6 = new Categoria(null, "Decoração");
		Categoria categoria7 = new Categoria(null, "Perfumaria");
		
		
		Produto produto1 = new Produto(null, "Computador", 5000.00);
		Produto produto2 = new Produto(null, "Impressora", 800.00);
		Produto produto3 = new Produto(null, "Mouse", 80.00);
		
		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		categoria2.getProdutos().addAll(Arrays.asList(produto2));
		
		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
		produto3.getCategorias().addAll(Arrays.asList(categoria1));
		
		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2,categoria3,categoria4,categoria5,categoria6,categoria7));
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
		
		SimpleDateFormat createAt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedido1 = new Pedido(null, createAt.parse("16/03/2019 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, createAt.parse("16/03/2019 10:40"), cliente1, endereco2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, createAt.parse("16/04/2019 00:00"), null);
		pedido2.setPagamento(pagto2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1, 0.00, 1, 2000.00);
		ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3, 0.00, 2, 80.00);
		ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2, 100.00, 1, 800.00);
		
		pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));
		pedido2.getItens().addAll(Arrays.asList(itemPedido3));
		
		produto1.getItens().addAll(Arrays.asList(itemPedido1));
		produto2.getItens().addAll(Arrays.asList(itemPedido3));
		produto3.getItens().addAll(Arrays.asList(itemPedido2));
		
		itemPedidoRepository.saveAll(Arrays.asList(itemPedido1,itemPedido2,itemPedido3));
		
	}

}