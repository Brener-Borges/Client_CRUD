package com.brenerborges.Client_CRUD.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brenerborges.Client_CRUD.dto.ClientDTO;
import com.brenerborges.Client_CRUD.dto.NewClientDTO;
import com.brenerborges.Client_CRUD.entities.Client;
import com.brenerborges.Client_CRUD.repositories.ClientRepository;
import com.brenerborges.Client_CRUD.services.exceptions.EntityNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly=true)
	public Page<ClientDTO> findAll(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(x -> new ClientDTO(x));
	}
	
	@Transactional(readOnly=true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return new ClientDTO(entity);
	}
	
	@Transactional(readOnly=true)
	public ClientDTO insert(ClientDTO obj) {
		obj.setId(null);
		Client entity = repository.save(fromClientDTO(obj));
		return new ClientDTO(entity);
	}
	
	public ClientDTO update(NewClientDTO newObj) {
		ClientDTO obj = findById(newObj.getId());
		updateData(newObj, obj);
		Client entity = repository.save(fromClientDTO(obj));
		return new ClientDTO(entity);
		
	}
	
	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);
	}
	
	public Client fromClientDTO(ClientDTO obj) {
		return new Client(obj.getId(), obj.getName(), obj.getCpf(), 
				obj.getIncome(), obj.getBirthDate().toString(), obj.getChildren());
	}
	
	public void updateData(NewClientDTO newObj, ClientDTO obj) {
		obj.setName(newObj.getName());
		obj.setIncome(newObj.getIncome());
		obj.setBirthDate(newObj.getBirthDate().toString());
		obj.setChildren(newObj.getChildren());
	}
}
