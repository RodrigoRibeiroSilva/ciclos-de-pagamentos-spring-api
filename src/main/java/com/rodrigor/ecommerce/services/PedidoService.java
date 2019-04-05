package com.rodrigor.ecommerce.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodrigor.ecommerce.domain.Cliente;
import com.rodrigor.ecommerce.domain.ItemPedido;
import com.rodrigor.ecommerce.domain.PagamentoComBoleto;
import com.rodrigor.ecommerce.domain.Pedido;
import com.rodrigor.ecommerce.domain.enums.EstadoPagamento;
import com.rodrigor.ecommerce.repositories.ItemPedidoRepository;
import com.rodrigor.ecommerce.repositories.PagamentoRepository;
import com.rodrigor.ecommerce.repositories.PedidoRepository;
import com.rodrigor.ecommerce.security.UserSS;
import com.rodrigor.ecommerce.services.exceptions.AuthorizationException;
import com.rodrigor.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private EmailService emailService;
	
	public Pedido findById(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		Cliente cliente = clienteService.find(obj.getCliente().getId());
		obj.setId(null);
		obj.setDataDoPedido(new Date());
		obj.setCliente(cliente);
		obj.getPagamento().setEstadoDoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getDataDoPedido());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.findById(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
		
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}

}
