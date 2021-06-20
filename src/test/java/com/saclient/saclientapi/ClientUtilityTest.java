package com.saclient.saclientapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.saclient.saclientapi.services.util.ClientUtility;

class ClientUtilityTest {

	@Mock
	ClientUtility utility;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testInitializeClientList() {
		when(utility.intializeCLientList()).thenCallRealMethod();
		assertEquals(5, utility.intializeCLientList().size());
	}
	
	@Test
	public void testCalculateCheckDigit() {
		when(utility.calculateCheckDigit(Mockito.anyString())).thenCallRealMethod();
		when(utility.checkAllNumbersInGivenString(Mockito.anyString())).thenCallRealMethod();
		when(utility.getSumOfDigits(Mockito.anyInt())).thenCallRealMethod();
		System.out.println(utility.calculateCheckDigit("861223376409"));
		System.out.println(utility.calculateCheckDigit("951208423518"));
		System.out.println(utility.calculateCheckDigit("671216723408"));
		assertEquals(4,utility.calculateCheckDigit("860604502309"));
		assertEquals(1,utility.calculateCheckDigit("960302302308"));
		assertEquals(2,utility.calculateCheckDigit("761203307618"));
		assertEquals(4,utility.calculateCheckDigit("960316723419"));
		assertEquals(4,utility.calculateCheckDigit("910331892308"));
		assertEquals(-1,utility.calculateCheckDigit("910331892scd"));
	}

	@Test
	public void testSumOfDigits() {
		when(utility.getSumOfDigits(Mockito.anyInt())).thenCallRealMethod();
		assertEquals(9,utility.getSumOfDigits(18));
		assertEquals(1,utility.getSumOfDigits(10));
		assertEquals(1,utility.getSumOfDigits(1));
	}
	
	@Test
	public void testCheckAllNumbersInGivenString() {
		when(utility.checkAllNumbersInGivenString(Mockito.anyString())).thenCallRealMethod();
		assertEquals(true,utility.checkAllNumbersInGivenString("1235434"));
		assertEquals(false,utility.checkAllNumbersInGivenString("1235434sdfcs"));
		assertEquals(false,utility.checkAllNumbersInGivenString("asdcs"));
		assertEquals(false,utility.checkAllNumbersInGivenString(""));
		assertEquals(false,utility.checkAllNumbersInGivenString(null));
	}
	
	@Test
	public void testIsValidSaId() {
		when(utility.isValidSaId(Mockito.anyString())).thenCallRealMethod();
		when(utility.calculateCheckDigit(Mockito.anyString())).thenCallRealMethod();
		when(utility.checkAllNumbersInGivenString(Mockito.anyString())).thenCallRealMethod();
		when(utility.getSumOfDigits(Mockito.anyInt())).thenCallRealMethod();
		assertEquals(true,utility.isValidSaId("8606045023094").isBlank());
		assertEquals(false,utility.isValidSaId("8615045023694").isBlank());
		assertEquals(false,utility.isValidSaId("8606445023094").isBlank());
		assertEquals(false,utility.isValidSaId("sdfs12332").isBlank());
		assertEquals(false,utility.isValidSaId("8606045023494").isBlank());
		assertEquals(false,utility.isValidSaId("8606045023024").isBlank());
		assertEquals(false,utility.isValidSaId("8606045023028").isBlank());
	}
	
	@Test
	public void testIsValidMobileNumber() {
		when(utility.isValidMobileNumber(Mockito.anyString())).thenCallRealMethod();
		when(utility.checkAllNumbersInGivenString(Mockito.anyString())).thenCallRealMethod();
		assertEquals(true, utility.isValidMobileNumber("9876543210"));
		assertFalse(utility.isValidMobileNumber("98543210"));
	} 
	
	
	
}
