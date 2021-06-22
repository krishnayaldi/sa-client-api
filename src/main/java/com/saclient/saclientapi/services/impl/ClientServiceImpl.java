package com.saclient.saclientapi.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saclient.saclientapi.errors.ClientApiError;
import com.saclient.saclientapi.pojos.Client;
import com.saclient.saclientapi.services.IClientService;
import com.saclient.saclientapi.services.IClientUtility;
import com.saclient.saclientapi.services.aspects.LoggingPointCut;

/**
 * @author Krishna
 *
 */
@Service(ClientServiceImpl.NAME)
public class ClientServiceImpl implements IClientService {

	public final static String NAME = "clientServiceImpl";

	@Autowired
	IClientUtility clientUtility;

	List<Client> listOfClients = new ArrayList<Client>();

	@PostConstruct
	public void init() {
		listOfClients = clientUtility.intializeCLientList();
	}

	@Override
	@LoggingPointCut
	public List<Client> getAllClients() {
		return listOfClients;
	}

	@Override
	@LoggingPointCut
	public Client createClient(Client client) {
		String idNumber = client.getIdNumber();
		String mobileNumber = client.getMobileNumber();
		if (isIdNumberAlreadyExists(idNumber)) {
			throw new ClientApiError("Duplicate SA ID");
		} else if (isMobileNoAllreadyExists(mobileNumber)) {
			throw new ClientApiError("Duplicate Mobile Number");
		}
		String res = clientUtility.isValidSaId(idNumber);
		if (!res.isBlank()) {
			throw new ClientApiError("Invalid SA ID with following errors [" + res + "]");
		} else if (!clientUtility.isValidMobileNumber(mobileNumber)) {
			throw new ClientApiError("Invalid mobile number");
		}
		listOfClients.add(client);
		return client;
	}

	private boolean isMobileNoAllreadyExists(String mobileNumber) {
		return listOfClients.stream().anyMatch(item -> item.getMobileNumber().equals(mobileNumber));
	}

	private boolean isIdNumberAlreadyExists(String saId) {
		return listOfClients.stream().anyMatch(item -> item.getIdNumber().equals(saId));
	}

	@Override
	@LoggingPointCut
	public Client updateClient(String saId, Client client) {
		if (Objects.isNull(saId) || saId.isBlank() || !clientUtility.checkAllNumbersInGivenString(saId)) {
			throw new ClientApiError("Invalid input to update a record");
		}
		Optional<Client> c = listOfClients.stream().filter(item -> item.getIdNumber().equals(saId)).findFirst();
		Client res = new Client();
		if (c.isPresent()) {
			final Client clnt  = c.get();
			if (Objects.isNull(client.getFirstName()) || Objects.isNull(client.getLastName())
					|| Objects.isNull(client.getIdNumber())
					|| !clientUtility.isValidSaId(client.getIdNumber()).isBlank()
					|| (Objects.nonNull(client.getMobileNumber()) && isIdNumberAlreadyExists(client.getMobileNumber()))
					|| (!saId.equals(client.getIdNumber()))) {
				throw new ClientApiError("Invalid input to update a record");
			}
			clnt.setFirstName(client.getFirstName());
			clnt.setLastName(client.getLastName());
			clnt.setIdNumber(client.getIdNumber());
			clnt.setMobileNumber(client.getMobileNumber());
			clnt.setPhysicalAddress(client.getPhysicalAddress());
			res = clnt;
		}else {
			throw new ClientApiError("No Matching client found with id number "+saId);
		}
		return res;
	}

	@Override
	@LoggingPointCut
	public List<Client> searchClientByFirstNameOrMobileNumberOrIdNumber(String input) {
		List<Client> resultantList = new ArrayList<Client>();

		List<Client> clientListMatchedByFirstName = listOfClients.stream()
				.filter(item -> (item.getFirstName().equalsIgnoreCase(input))).collect(Collectors.toList());

		List<Client> clientListMatchedByIdNumber = listOfClients.stream()
				.filter(item -> (item.getIdNumber().equals(input))).collect(Collectors.toList());

		List<Client> clientListMatchedByMobileNumber = listOfClients.stream()
				.filter(item -> (Objects.isNull(item.getMobileNumber()) ? false : input.equals(item.getMobileNumber())))
				.collect(Collectors.toList());

		resultantList.addAll(clientListMatchedByFirstName);
		resultantList.addAll(clientListMatchedByIdNumber);
		resultantList.addAll(clientListMatchedByMobileNumber);

		return resultantList;
	}

	@Override
	@LoggingPointCut
	public boolean validateSaId(String saId) {
		String res = clientUtility.isValidSaId(saId);
		if (res.isBlank())
			return true;
		else
			return false;
	}

	@Override
	public Client deleteClient(Client client) {
		if(!listOfClients.contains(client)) {
			throw new ClientApiError("client was not found");
		}else {
			listOfClients.remove(client);
		}
		return client;
	}
		

}
