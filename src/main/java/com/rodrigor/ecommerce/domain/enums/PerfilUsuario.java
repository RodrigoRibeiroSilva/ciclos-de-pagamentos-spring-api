package com.rodrigor.ecommerce.domain.enums;

public enum PerfilUsuario {
	
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int codigo;
	private String descricao;
	
	private PerfilUsuario(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static PerfilUsuario toEnum(Integer codigo) {
		if(codigo == null) {
			return null;
		}
		
		for (PerfilUsuario perfil : PerfilUsuario.values()) {
			if(codigo.equals(perfil.getCodigo())) {
				return perfil;
			}
		}
		
		throw new IllegalArgumentException("Código: " + codigo + "inválido");
	}

}
