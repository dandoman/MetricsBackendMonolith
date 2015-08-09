package com.cante.metrics.dao;

import com.cante.metrics.entity.CustomerEntity;
import com.cante.metrics.entity.pojo.Customer;

public interface CustomerDao {
	public Customer create(CustomerEntity c);
	public CustomerEntity getUserForEmailLogin(String email);
	public boolean isValidKey(String apiKey);
}
