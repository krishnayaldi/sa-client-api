package com.saclient.saclientapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import com.saclient.saclientapi.errors.ClientApiError;
import com.saclient.saclientapi.pojos.Client;
import com.saclient.saclientapi.services.IClientUtility;
import com.saclient.saclientapi.services.impl.ClientServiceImpl;
import com.saclient.saclientapi.services.util.ClientUtility;

class ClientServiceImplTest {

	@Mock
	ClientServiceImpl clientService;
	
	@Mock
	IClientUtility utility;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		List<Client> list = new ArrayList<Client>();

		list.add(new Client("Adam", "Gustombe", "8823112211", "8606045023094", "Johanseberg"));
		list.add(new Client("Ana", "Rod", "8823112212", "8606045023021", "Johanseberg"));
		list.add(new Client("Jack", "Garvey", "8823112213", "8606045023122", "Tanzania"));
		list.add(new Client("Berry", "Geller", "8823112214", "8606045023024", "Capetown"));
		list.add(new Client("Chad", "McLen", "8823112215", "8606045023024", "Johanseberg"));

		ReflectionTestUtils.setField(clientService, "listOfClients", list);
		ReflectionTestUtils.setField(clientService, "clientUtility", utility);
	}
	
	@Test
	void testCreateClientErrorMessage1() {
		Client client = new Client("Adam", "Gustombe", "8823112211", "8606045023094", "Johanseberg");
		when(clientService.createClient(Mockito.any(Client.class))).thenCallRealMethod();
		try {
			clientService.createClient(client);
		}catch(ClientApiError e) {
			assertEquals("Duplicate SA ID", e.getMessage());
		}
	}
	
	@Test
	void testCreateClientErrorMessage2() {
		Client client = new Client("Adam", "Gustombe", "8823112211", "8606075023094", "Johanseberg");
		when(clientService.createClient(Mockito.any(Client.class))).thenCallRealMethod();
		try {
			clientService.createClient(client);
		}catch(ClientApiError e) {
			assertEquals("Duplicate Mobile Number", e.getMessage());
		}
	}
	
	@Test
	void testCreateClientErrorMessage3() {
		Client client = new Client("Adam", "Gustombe", "8823116754", "8606075023094", "Johanseberg");
		when(clientService.createClient(Mockito.any(Client.class))).thenCallRealMethod();
		when(utility.isValidSaId(Mockito.anyString())).thenReturn("some");
		try {
			clientService.createClient(client);
		}catch(ClientApiError e) {
			assertEquals("Invalid SA ID with following errors [some]", e.getMessage());
		}
	}
	
	@Test
	void testCreateClientErrorMessage4() {
		Client client = new Client("Adam", "Gustombe", "88232212", "8611045023094", "Johanseberg");
		when(clientService.createClient(Mockito.any(Client.class))).thenCallRealMethod();
		when(utility.isValidSaId(Mockito.anyString())).thenReturn("");
		try {
			clientService.createClient(client);
		}catch(ClientApiError e) {
			assertEquals("Invalid mobile number", e.getMessage());
		}
	}
	
	@Test
	void testCreateClient() {
		Client client = new Client("Thomas", "Cofee", "7623892312", "8612235023096", "capetown");
		when(clientService.createClient(Mockito.any(Client.class))).thenCallRealMethod();
		when(utility.isValidSaId(Mockito.anyString())).thenReturn("");
		when(utility.isValidMobileNumber(Mockito.anyString())).thenReturn(true);
		Client c = clientService.createClient(client);
		assertEquals(client, c);
	}

	@Test
	void testSearchMethod() {
		when(clientService.searchClientByFirstNameOrMobileNumberOrIdNumber(Mockito.anyString())).thenCallRealMethod();
		List<Client> list1 = clientService.searchClientByFirstNameOrMobileNumberOrIdNumber("ana");
		assertEquals(1, list1.size());
		
		List<Client> list2 = clientService.searchClientByFirstNameOrMobileNumberOrIdNumber("8606045023122");
		assertEquals(1, list2.size());
		
		List<Client> list3 = clientService.searchClientByFirstNameOrMobileNumberOrIdNumber("8823112215");
		assertEquals(1, list3.size());
	}
	
	@Test
	public void testUpdateClient() {
		when(clientService.updateClient(Mockito.anyString(), Mockito.any(Client.class))).thenCallRealMethod();
		when(utility.checkAllNumbersInGivenString(Mockito.anyString())).thenReturn(true);
		when(utility.isValidSaId(Mockito.anyString())).thenReturn("");
		Client c = new Client("Mark", "Billyard", "8823112211", "8606045023094", "Johanseberg");
		Client recieved = clientService.updateClient("8606045023094", c);
		assertEquals(c, recieved);
		Client c1 = new Client("Mark", "Billyard", "8823112211", "8606045023094", "Johanseberg");
		try{
			recieved = clientService.updateClient("sd2442", c1);
		}catch(ClientApiError e) {
			assertEquals("No Matching client found with id number sd2442", e.getMessage());
		}
		Client c2 = new Client("Mark", "Billyard", "8823112211", "8606045023094", "Johanseberg");
		
		try{
			recieved = clientService.updateClient("8606045023024", c2);
		}catch(ClientApiError e) {
			assertEquals("Invalid input to update a record", e.getMessage());
		}
	}
}
