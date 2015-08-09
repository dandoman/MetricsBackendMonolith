package com.cante.metrics.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cante.metrics.entity.pojo.AccountType;
import com.cante.metrics.entity.pojo.Customer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "customers")
@NoArgsConstructor
public class CustomerEntity {
	@Id private String id;
	private String organizationName;
	private String organizationAddress;
	private String contactName;
	private String contactPhoneNumber;
	private String contactEmail;
	private String passwordHash;
	private String passwordSalt;
	private String apiKey;
	private AccountType accountType;
	private Date creationDate;
	private Date lastUpdatedDate;
	private int recordVersionNumber;
	
	//Billing info
	private String billingName;
	private String billingAddress;
	private int expiryMonth;
	private int expiryYear;
	private int cvsCode;
	private String cardNumberEncrypted;
	private String last4Digits;
	
	public Customer toCustomer(){
		Customer c = new Customer();
		c.setAccountType(this.accountType);
		c.setApiKey(this.apiKey);
		c.setBillingAddress(this.billingAddress);
		c.setBillingName(this.billingName);
		c.setContactEmail(this.contactEmail);
		c.setContactName(this.contactName);
		c.setContactPhoneNumber(this.contactPhoneNumber);
		c.setCvsCode(this.cvsCode);
		c.setExpiryMonth(this.expiryMonth);
		c.setExpiryYear(this.expiryYear);
		c.setId(this.id);
		c.setLast4Digits(this.last4Digits);
		c.setOrganizationAddress(this.organizationAddress);
		c.setOrganizationName(this.organizationName);
		return c;
	}
}
