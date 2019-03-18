package com.rodrigor.ecommerce.domain;

import javax.persistence.Entity;

import com.rodrigor.ecommerce.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComCartao extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	private Integer numeroParcelas;
	
	public PagamentoComCartao() {
		
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estadoDoPagamento, Pedido pedido, Integer numeroParcelas) {
		super(id, estadoDoPagamento, pedido);
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
