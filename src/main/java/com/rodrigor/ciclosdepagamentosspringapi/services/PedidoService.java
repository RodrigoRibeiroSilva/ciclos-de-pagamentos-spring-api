package com.rodrigor.ciclosdepagamentosspringapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigor.ciclosdepagamentosspringapi.domain.Pedido;
import com.rodrigor.ciclosdepagamentosspringapi.repositories.PedidoRepository;
import com.rodrigor.ciclosdepagamentosspringapi.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido findById(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

}
