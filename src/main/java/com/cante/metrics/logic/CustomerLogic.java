package com.cante.metrics.logic;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.cante.metrics.dao.CustomerDao;
import com.cante.metrics.entity.CustomerEntity;
import com.cante.metrics.entity.pojo.Customer;
import com.cante.metrics.exception.BadArgsException;
import com.cante.metrics.exception.NotAuthorizedException;
import com.cante.metrics.exception.NotFoundException;
import com.cante.metrics.request.CreateCustomerRequest;
import com.cante.metrics.request.UpdateCustomerRequest;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomerLogic {
	@Setter
	private CustomerDao customerDao;
	//lol @ keeping the key here, doesn't matter, won't actually be using it in the end
	private static final String ENCRYPTION_KEY_PASSPHRASE = "acdd21d8-1352-4149-a0c6-945a73de4336";
	private SecretKeySpec ENCRYPTION_KEY = null;

	public Customer createCustomer(CreateCustomerRequest r) {
		CustomerEntity c = new CustomerEntity();
		c.setId(UUID.randomUUID().toString());
		c.setApiKey(UUID.randomUUID().toString());

		c.setAccountType(r.getAccountType());
		c.setBillingAddress(r.getBillingAddress());
		c.setBillingName(r.getBillingName());
		c.setContactEmail(r.getContactEmail());
		c.setContactName(r.getContactName());
		c.setContactPhoneNumber(r.getContactPhoneNumber());
		c.setOrganizationAddress(r.getOrganizationAddress());
		c.setOrganizationName(r.getOrganizationName());

		// validate card number,code,month,year encrypt, get last 4 digits
		String last4Digits = validateCardAndGetLastDigits(
				r.getCreditCardNumber(), r.getCvsCode(), r.getExpiryMonth(),
				r.getExpiryYear());
		String encryptedCardNo = encrypt(r.getCreditCardNumber());
		c.setCardNumberEncrypted(encryptedCardNo);
		c.setCvvCode(r.getCvsCode());
		c.setExpiryMonth(r.getExpiryMonth());
		c.setExpiryYear(r.getExpiryYear());
		c.setLast4Digits(last4Digits);

		// Hash and salt the password
		String salt = UUID.randomUUID().toString();
		c.setPasswordSalt(salt);
		c.setPasswordHash(hash(r.getPassword() + salt));
		return customerDao.create(c);
	}

	private String hash(String saltedPassword) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(saltedPassword.getBytes("UTF-8"));
			return Hex.encodeHexString(md.digest());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException("Unable to create account", e);
		}
	}

	private String decrypt(String creditCardEncryptedHex) {
		if (ENCRYPTION_KEY == null) {
			initKey();
		}
		
		try{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, ENCRYPTION_KEY);
			byte[] decrypted = cipher.doFinal(Hex.decodeHex(creditCardEncryptedHex.toCharArray()));
			return new String(decrypted,"UTF-8");
		} catch(Exception e){
			throw new RuntimeException("Unable to decrypt credit card number", e);
		}
	}

	private String encrypt(String creditCardNumber) {
		if (ENCRYPTION_KEY == null) {
			initKey();
		}
		
		try{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, ENCRYPTION_KEY);
			byte[] encrypted = cipher.doFinal(creditCardNumber.getBytes("UTF-8"));
			return Hex.encodeHexString(encrypted);
		} catch(Exception e){
			throw new RuntimeException("Unable to encrypt credit card number", e);
		}
	}

	private void initKey() {
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			byte[] key = sha1.digest(ENCRYPTION_KEY_PASSPHRASE.getBytes());
			key = Arrays.copyOf(key, 16); // Use first 128 bits
			ENCRYPTION_KEY = new SecretKeySpec(key, "AES");
			sha1 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Unable to use crypto functions", e);
		}
	}

	/*
	 * Returns card's last 4 digits
	 */
	private String validateCardAndGetLastDigits(String creditCardNumber,
			int cvsCode, int expiryMonth, int expiryYear) {

		if (expiryYear < new DateTime().getYear()) {
			throw new BadArgsException(expiryYear + " < than current year");
		}

		if (expiryMonth < 1 || expiryMonth > 12) {
			throw new BadArgsException(expiryMonth + " is not a valid month");
		}

		if (cvsCode < 0 || cvsCode > 9999) {
			throw new BadArgsException("Invalid cvs code");
		}

		if (creditCardNumber.length() != 16) {
			throw new BadArgsException("Invalid credit card number");
		}

		// Ghetto, use regex?
		for (char c : creditCardNumber.toCharArray()) {
			if (c < '0' || c > '9') {
				throw new BadArgsException(
						"Invalid credit card number, numeric values only");
			}
		}
		return creditCardNumber.substring(12, 16);
	}

	public Customer login(String email, String password) {
		CustomerEntity c = customerDao.getUserForEmailLogin(email);
		String processedPassword = hash(password + c.getPasswordSalt());
		if(c.getPasswordHash().equals(processedPassword)){
			return c.toCustomer();
		}
		else{
			throw new NotAuthorizedException("Invalid email or password");
		}
	}
	
	public Customer getCustomerForAPIKey(String apiKey) {
		return customerDao.getCustomerForAPIKey(apiKey);
	}

	public Customer getCustomerById(String customerId) {
		return customerDao.getCustomerById(customerId);
	}

	public Customer updateCustomer(String id, UpdateCustomerRequest r) {
		CustomerEntity c = customerDao.getCustomerEntityById(id);
		if(c == null) {
			throw new NotFoundException("Customer not found");
		}
		c.setAccountType(r.getAccountType());
		c.setBillingAddress(r.getBillingAddress());
		c.setBillingName(r.getBillingName());
		c.setContactEmail(r.getContactEmail());
		c.setContactName(r.getContactName());
		c.setContactPhoneNumber(r.getContactPhoneNumber());
		c.setOrganizationAddress(r.getOrganizationAddress());
		c.setOrganizationName(r.getOrganizationName());
		
		if(!StringUtils.isEmpty(r.getPassword())) {
			String salt = UUID.randomUUID().toString();
			c.setPasswordSalt(salt);
			c.setPasswordHash(hash(r.getPassword() + salt));
		}
		return customerDao.updateCustomer(c);
	}
}
