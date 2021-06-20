package com.saclient.saclientapi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saclient.saclientapi.pojos.Client;
import com.saclient.saclientapi.services.IClientService;
import com.saclient.saclientapi.services.aspects.LoggingPointCut;

@RestController
public class ClientController {

	@Autowired
	IClientService clientService;
	
	@GetMapping("/clients")
	@LoggingPointCut
	public List<Client> getListOfAllClients(){
		return clientService.getAllClients();
	}
	
	@PostMapping("/client")
	@LoggingPointCut
	public Client createClient(@RequestBody @Valid Client client) {
		return clientService.createClient(client);
	}
	
	@GetMapping("/clients/{input}")
	@LoggingPointCut
	public List<Client> getClientListByFirstNameOrIdNumberOrMobileNumber(@PathVariable String input){
		return clientService.searchClientByFirstNameOrMobileNumberOrIdNumber(input);
	}
	
	@PutMapping("/clients/{idNumber}")
	@LoggingPointCut
	public Client updateClientById(@PathVariable String idNumber,@RequestBody @Valid Client client) {
		return clientService.updateClient(idNumber, client);
	}
	
	@GetMapping("/clients/validateSaId/{saId}")
	@LoggingPointCut
	public boolean validateSaId(@PathVariable String saId) {
		return clientService.validateSaId(saId);
	}
}
