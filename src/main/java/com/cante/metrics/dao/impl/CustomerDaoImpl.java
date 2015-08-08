package com.cante.metrics.dao.impl;

import lombok.Setter;

import org.hibernate.SessionFactory;

import com.cante.metrics.dao.CustomerDao;

public class CustomerDaoImpl implements CustomerDao{
	@Setter private SessionFactory sessionFactory;
}
