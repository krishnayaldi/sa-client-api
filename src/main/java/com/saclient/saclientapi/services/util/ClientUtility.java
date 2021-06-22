package com.saclient.saclientapi.services.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.saclient.saclientapi.pojos.Client;
import com.saclient.saclientapi.services.IClientUtility;
import com.saclient.saclientapi.services.aspects.LoggingPointCut;

@Service(ClientUtility.NAME)
public class ClientUtility implements IClientUtility{

	public static final String NAME = "clientUtility";

	@Override
	@LoggingPointCut
	public List<Client> intializeCLientList() {
		List<Client> list = new ArrayList<Client>();

		list.add(new Client("Adam", "Gustombe", "8823112211", "8606045023094", "Johanseberg"));
		list.add(new Client("Ana", "Rod", "8823112212", "8606045023021", "Johanseberg"));
		list.add(new Client("Jack", "Garvey", "8823112213", "8606045023122", "Tanzania"));
		list.add(new Client("Berry", "Geller", "8823112214", "8606045023024", "Capetown"));
		list.add(new Client("Chad", "McLen", "8823112215", "8606045023024", "Johanseberg"));
		
		return list;
	}

	/**
	 * source https://en.wikipedia.org/wiki/Luhn_algorithm
	 * 7992739871
	 * 1. Double every second digit, from the rightmost: (1×2) = 2, (8×2) = 16, (3×2) = 6, (2×2) = 4, (9×2) = 18
	 * 2. Sum all the individual digits (digits in parentheses are the products from Step 1): x 
	 * 	  (the check digit) + (2) + 7 + (1+6) + 9 + (6) + 7 + (4) + 9 + (1+8) + 7 = x + 67.
	 * 3. Multiply by 9 (603).
	 * 4. 603 mod 10 is then 3, which is the check digit. Thus, x=3.
	 * */
	@Override
	@LoggingPointCut
	public int calculateCheckDigit(String no) {
		var res = -1;
		if (Objects.isNull(no) || no.length() != 12 || !checkAllNumbersInGivenString(no))
			return res;
		else {
			var sum = 0;
			var tNo = 0;
			char arr[] = no.toCharArray();
			for (var i = 0; i < arr.length; i++) {
				// for loop to double every other digit and make sum of all digits
				tNo = Integer.parseInt("" + arr[i]);
				if (i % 2 != 0) {
					sum = sum + ((tNo * 2 > 9) ? getSumOfDigits(tNo * 2) : tNo * 2);
				} else {
					sum = sum + tNo;
				}
			}
			sum = sum * 9;
			res = 10 - sum % 10;
			res = res == 10 ? 1 : res;
		}
		return res;
	}

	public int getSumOfDigits(int i) {
		if (i < 10) {
			return i;
		} else {
			var res = 0;
			var no = i;
			while (no != 0) {
				res = res + no % 10;
				no = no / 10;
			}
			return res;
		}
	}

	@Override
	@LoggingPointCut
	public boolean checkAllNumbersInGivenString(String no) {
		if (Objects.isNull(no) || no.length() == 0)
			return false;
		else {
			List<Character> listOfChars = no.chars().mapToObj(item -> ((char) item)).filter(e -> Character.isDigit(e))
					.collect(Collectors.toList());
			if (listOfChars.size() == no.length())
				return true;
			return false;
		}
	}

	/**
	 * https://en.wikipedia.org/wiki/South_African_identity_card SA number rules
	 * YYMMDD SSSS CAZ
	 * Field	Description
	 * YYMMDD	Date of birth
	 * SSSS		Sequential number: 0000–4999 for females and 5000–9999 for males.
	 * C		Status: 0 = South African citizen, 1 = non-SA-born permanent resident.
	 * A		8 or 9
	 * Z		Check digit used to validate the ID Number, which is calculated using the Luhn algorithm.
	 * */
	@Override
	@LoggingPointCut
	public String isValidSaId(String idNumber) {
		StringBuilder res = new StringBuilder();
		if (Objects.isNull(idNumber) || idNumber.length() == 0 || !checkAllNumbersInGivenString(idNumber)) {
			res.append("Id is null or it has aplhabets in it.");
		} else {
			int yy = Integer.parseInt(idNumber.substring(0, 2));
			int mm = Integer.parseInt(idNumber.substring(2, 4));
			int dd = Integer.parseInt(idNumber.substring(4, 6));
			if (!checkIfDateIsValid(yy, mm, dd)) {
				res = res.length() == 0 ? res.append(" Invalid date ") : res.append(" And Invalid Date");
			}
			int c = Integer.parseInt(idNumber.substring(10, 11));
			if (c < 0 || c > 1) {
				res = res.length() == 0 ? res.append(" Citizen Status is not 0 or 1")
						: res.append(" And Citizen Status is not 0 or 1");
			}
			int a = Integer.parseInt(idNumber.substring(11, 12));
			if (a < 8 || a > 9)
				res = res.length() == 0 ? res.append(" A is not 8 or 9") : res.append(" And A is not 8 or 9");
			int z = Integer.parseInt(idNumber.substring(12));
			if (z != calculateCheckDigit(idNumber.substring(0, 12)))
				res = res.length() == 0 ? res.append(" check digit is not valid")
						: res.append(" And check digit is not valid");
		}
		return res.toString();
	}
	
	private boolean checkIfDateIsValid(int yy, int mm, int dd) {
		var ccyy = yy <= 21 ? 2000+yy : 1900+yy;
		 boolean dateIsValid = true;
		    try {
		        LocalDate.of(ccyy, mm, dd);
		    } catch (DateTimeException e) {
		        dateIsValid = false;
		    }
		    return dateIsValid;
	}

	@Override
	@LoggingPointCut
	public boolean isValidMobileNumber(String mobileNumber) {
		if (Objects.isNull(mobileNumber) || mobileNumber.isBlank() || !checkAllNumbersInGivenString(mobileNumber)
				|| mobileNumber.length() != 10) {
			return false;
		} else {
			return true;
		}
	}
}
