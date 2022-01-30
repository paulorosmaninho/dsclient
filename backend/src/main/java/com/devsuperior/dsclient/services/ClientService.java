package com.devsuperior.dsclient.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsclient.dtos.ClientDTO;
import com.devsuperior.dsclient.entities.Client;
import com.devsuperior.dsclient.repositories.ClientRepository;
import com.devsuperior.dsclient.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {


	@Autowired
	ClientRepository clientRepository;


	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(Pageable pageRequest) {

		Page<Client> pageEntity = clientRepository.findAll(pageRequest);

		Page<ClientDTO> pageDto = pageEntity.map(pageElement -> new ClientDTO(pageElement));

		return pageDto;

	}


	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {

		Optional<Client> objOptional = clientRepository.findById(id);

		Client entity = objOptional.orElseThrow(() -> new ResourceNotFoundException("Cliente " + id + " não encontrado"));

		return new ClientDTO(entity);
	}



	@Transactional
	public ClientDTO insert(ClientDTO clientDto) {

		Client entity = new Client();

		copyDtoToEntity(clientDto, entity);

		entity = clientRepository.save(entity);

		return new ClientDTO(entity);
	}



	@Transactional
	public ClientDTO update(Long id, ClientDTO updatedClientDTO) {

		try {
			Client entity = clientRepository.getById(id);

			copyDtoToEntity(updatedClientDTO, entity);

			entity = clientRepository.save(entity);

			return new ClientDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Cliente " + id + " não encontrado");
		}
	}



	@Transactional
	public void delete(Long id) {

		try {
			
			clientRepository.deleteById(id);

		}catch(EmptyResultDataAccessException e) {
			
			throw new ResourceNotFoundException("Cliente " + id + " não encontrado");
			
		}
	}


	
	private void copyDtoToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}


}
