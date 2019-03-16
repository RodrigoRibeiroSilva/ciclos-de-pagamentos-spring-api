package com.rodrigor.ciclosdepagamentosspringapi.domain;

import com.rodrigor.ciclosdepagamentosspringapi.domain.enums.EstadoPagamento;

public class PagamentoComCartao extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	private Integer numeroParcelas;
	
	public PagamentoComCartao() {
		
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estadoDoPagamento, Pedido pedido, Cliente cliente, Integer numeroParcelas) {
		super(id, estadoDoPagamento, pedido, cliente);
		this.numeroParcelas = numeroParcelas;
	}

	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	@Override
	public String toString() {
		return "PagamentoComCartao [numeroParcelas=" + numeroParcelas + "]";
	}
}
