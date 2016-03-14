package com.cante.metrics.request;

import lombok.Data;

import com.cante.metrics.entity.pojo.AccountType;

@Data
public class UpdateCustomerRequest {
	private String organizationName;
	private String organizationAddress;
	private String contactName;
	private String contactPhoneNumber;
	private String contactEmail;
	private String password;
	private AccountType accountType;
	
	private String billingName;
	private String billingAddress;
	private int expiryMonth;
	private int expiryYear;
	private int cvsCode;
	private String creditCardNumber;
}
