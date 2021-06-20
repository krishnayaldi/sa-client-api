/**
 * 
 */
package com.saclient.saclientapi.services;

import java.util.List;

import com.saclient.saclientapi.pojos.Client;

/**
 * @author Krishna
 *
 */
public interface IClientService {

	public List<Client> getAllClients();
	
	public Client createClient(Client client);

	public Client updateClient(String saId, Client client);
	
	public List<Client> searchClientByFirstNameOrMobileNumberOrIdNumber(String input);
	
	public boolean validateSaId(String saId);
}
