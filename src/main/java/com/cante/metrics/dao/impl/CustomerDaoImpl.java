package com.cante.metrics.dao.impl;

import java.util.Date;

import lombok.Setter;

import org.hibernate.SessionFactory;

import com.cante.metrics.dao.CustomerDao;
import com.cante.metrics.entity.CustomerEntity;
import com.cante.metrics.entity.pojo.Customer;

public class CustomerDaoImpl implements CustomerDao{
	@Setter private SessionFactory sessionFactory;

	@Override
	public Customer create(CustomerEntity c) {
		Date now = new Date();
		c.setCreationDate(now);
		c.setLastUpdatedDate(now);
		c.setRecordVersionNumber(0);
		sessionFactory.getCurrentSession().save(c);
		return c.toCustomer();
	}
}
