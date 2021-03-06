package com.rodrigor.ecommerce.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rodrigor.ecommerce.domain.Cidade;
import com.rodrigor.ecommerce.domain.Cliente;
import com.rodrigor.ecommerce.domain.Endereco;
import com.rodrigor.ecommerce.domain.enums.PerfilUsuario;
import com.rodrigor.ecommerce.domain.enums.TipoCliente;
import com.rodrigor.ecommerce.dto.ClienteDTO;
import com.rodrigor.ecommerce.dto.ClienteNewDTO;
import com.rodrigor.ecommerce.repositories.ClienteRepository;
import com.rodrigor.ecommerce.security.UserSS;
import com.rodrigor.ecommerce.services.exceptions.AuthorizationException;
import com.rodrigor.ecommerce.services.exceptions.DataIntegrityException;
import com.rodrigor.ecommerce.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder encrypt;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private S3Service s3Service;
	
	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		
		if(user == null || !user.hasRole(PerfilUsuario.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}
	
	public Cliente findByEmail(String email) {
		return repo.findByEmail(email);
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		return s3Service.uploadFile(multipartFile);
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), encrypt.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
