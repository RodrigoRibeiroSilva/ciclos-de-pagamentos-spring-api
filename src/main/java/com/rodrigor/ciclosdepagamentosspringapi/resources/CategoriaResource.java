package com.rodrigor.ciclosdepagamentosspringapi.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigor.ciclosdepagamentosspringapi.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Categoria> listar() {
		Categoria categoria = new Categoria(1, "Informática");
		List<Categoria> listaCategoria = new ArrayList<Categoria>();
		
		listaCategoria.add(categoria);
		return listaCategoria;
	}

}
