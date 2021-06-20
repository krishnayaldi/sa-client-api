package com.saclient.saclientapi.services;

import java.util.List;

import com.saclient.saclientapi.pojos.Client;

public interface IClientUtility {

	public List<Client> intializeCLientList();
	
	public int calculateCheckDigit(String no);
	
	public boolean checkAllNumbersInGivenString(String no);
	
	public String isValidSaId(String idNumber);
	
	public boolean isValidMobileNumber(String mobileNumber);
	
}
