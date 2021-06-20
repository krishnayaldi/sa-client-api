package com.saclient.saclientapi.pojos;

import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * @author Krishna
 *
 */
public class Client {

	@NotNull(message = "FirstName is mandatory")
	String firstName;
	@NotNull(message = "LastName is mandatory")
	String lastName;
	String mobileNumber;
	@NotNull(message = "IdNumber is mandatory")
	String idNumber;
	String physicalAddress;
	
	public Client() {
		super();
	}

	public Client(String firstName, String lastName, String mobileNumber, String idNumber, String physicalAddress) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.idNumber = idNumber;
		this.physicalAddress = physicalAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, idNumber, lastName, mobileNumber, physicalAddress);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Client)) {
			return false;
		}
		Client other = (Client) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(idNumber, other.idNumber)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(mobileNumber, other.mobileNumber)
				&& Objects.equals(physicalAddress, other.physicalAddress);
	}
	
}
