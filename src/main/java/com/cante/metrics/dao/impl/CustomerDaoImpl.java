package com.cante.metrics.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Setter;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.cante.metrics.dao.CustomerDao;
import com.cante.metrics.entity.CustomerEntity;
import com.cante.metrics.entity.MetricEntity;
import com.cante.metrics.entity.StagedMetricEntity;
import com.cante.metrics.entity.pojo.Customer;
import com.cante.metrics.entity.pojo.StagedMetric;
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

	@Override
	public Customer getCustomerById(String customerId) {
		CustomerEntity custEntity =  (CustomerEntity) sessionFactory.getCurrentSession().get(CustomerEntity.class, customerId);
		if(custEntity == null) {
			return null;
		}
		
		return custEntity.toCustomer();
	}

	@Override
	public Customer updateCustomer(CustomerEntity c) {
		sessionFactory.getCurrentSession().save(c);
		return c.toCustomer();
	}

	@Override
	public List<String> getAllCustomerIds() {
		List<CustomerEntity> entityList = sessionFactory
				.getCurrentSession()
				.createSQLQuery("SELECT * FROM customers")
				.addEntity(CustomerEntity.class).list();
		List<String> customerIds = new ArrayList<String>();
		if(entityList == null) {
			return new ArrayList<String>();
		}
		
		for (CustomerEntity entity : entityList) {
			customerIds.add(entity.getId());
		}
		return customerIds;
	}

	@Override
	public CustomerEntity getCustomerEntityById(String customerId) {
		return (CustomerEntity) sessionFactory.getCurrentSession().get(CustomerEntity.class, customerId);
	}	
}
