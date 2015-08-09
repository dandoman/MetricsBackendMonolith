package com.cante.metrics.entity.pojo;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
	private String id;
	private String organizationName;
	private String organizationAddress;
	private String contactName;
	private String contactPhoneNumber;
	private String contactEmail;
	private String apiKey;
	private AccountType accountType;
	
	//Billing info
	private String billingName;
	private String billingAddress;
	private int expiryMonth;
	private int expiryYear;
	private int cvsCode;
	private String last4Digits;
}
