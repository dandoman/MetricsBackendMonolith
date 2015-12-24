package com.cante.metrics.dao.impl;

import java.util.Date;

import lombok.Setter;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.cante.metrics.dao.CustomerDao;
import com.cante.metrics.entity.CustomerEntity;
import com.cante.metrics.entity.MetricEntity;
import com.cante.metrics.entity.pojo.Customer;
import com.cante.metrics.exception.NotAuthorizedException;
import com.cante.metrics.exception.NotFoundException;

public class CustomerDaoImpl implements CustomerDao {
	@Setter
	private SessionFactory sessionFactory;

	@Override
	public Customer create(CustomerEntity c) {
		Date now = new Date();
		c.setCreationDate(now);
		c.setLastUpdatedDate(now);
		c.setRecordVersionNumber(0);
		sessionFactory.getCurrentSession().save(c);
		return c.toCustomer();
	}

	@Override
	public CustomerEntity getUserForEmailLogin(String email) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(
				CustomerEntity.class);
		c.add(Restrictions.eq("contactEmail", email));
		CustomerEntity customer = (CustomerEntity) c.uniqueResult();
		if(customer == null){
			//Don't want to allow attackers to use this to validate email addresses
			throw new NotAuthorizedException("Invalid email or password");
		}
		return customer;
	}
	
	@Override
	public Customer getCustomerForAPIKey(String apiKey) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(
				CustomerEntity.class);
		c.add(Restrictions.eq("apiKey", apiKey));
		CustomerEntity customer = (CustomerEntity) c.uniqueResult();
		if(customer == null){
			return null;
		}
		return customer.toCustomer();
	}
	
}
