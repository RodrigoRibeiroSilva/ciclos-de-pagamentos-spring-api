package com.rodrigor.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rodrigor.ecommerce.domain.Cliente;
import com.rodrigor.ecommerce.repositories.ClienteRepository;
import com.rodrigor.ecommerce.security.UserSS;

@Service
public class UserDetailsServiceImp implements UserDetailsService{
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = repo.findByEmail(email);
		
		if(cliente.equals(null)) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfilUsuario());
	}

}
