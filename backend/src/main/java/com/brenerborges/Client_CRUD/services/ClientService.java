package com.brenerborges.Client_CRUD.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brenerborges.Client_CRUD.dto.ClientDTO;
import com.brenerborges.Client_CRUD.entities.Client;
import com.brenerborges.Client_CRUD.repositories.ClientRepository;
import com.brenerborges.Client_CRUD.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService{

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id){
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO insert(ClientDTO obj){
		Client entity = new Client();
		fromClientDTO(entity, obj);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO obj){
		try{
			Client entity = repository.getOne(id);
			fromClientDTO(entity, obj);
			entity = repository.save(entity);
			return new ClientDTO(entity);
		}
		catch(EntityNotFoundException e){
			throw new ResourceNotFoundException("Resource not found! Id: " + id);
		}	
	}

	public void delete(Long id){
		try{
			repository.deleteById(id);
		}
		catch(EntityNotFoundException e){
			throw new ResourceNotFoundException("Resource not found! Id: " + id);
		}	
	}
	
	public void fromClientDTO(Client entity, ClientDTO obj) {
		entity.setName(obj.getName());
		entity.setBirthDate(obj.getBirthDate());
		entity.setIncome(obj.getIncome());
		entity.setCpf(obj.getCpf());
		entity.setChildren(obj.getChildren());
	}
}
