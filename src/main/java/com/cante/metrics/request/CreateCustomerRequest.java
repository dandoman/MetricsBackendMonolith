package com.cante.metrics.request;

import java.util.Date;

import lombok.Data;

import com.cante.metrics.entity.pojo.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCustomerRequest {
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
