package com.cante.metrics.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Setter;

import org.hibernate.SessionFactory;

import com.cante.metrics.dao.MetricDao;
import com.cante.metrics.entity.MetricEntity;
import com.cante.metrics.entity.pojo.Metric;

public class MetricDaoImpl implements MetricDao {
	@Setter private SessionFactory sessionFactory;

	public void create(String ownerId, Metric m) {
		MetricEntity metric = new MetricEntity(m);
		metric.setId(UUID.randomUUID().toString());
		metric.setOwnerId(ownerId);
		metric.setCreationDate(new Date());
		sessionFactory.getCurrentSession().save(metric);
	}
	
	public List<Metric> getAllMetrics(){
		List<MetricEntity> entityList = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM metrics").addEntity(MetricEntity.class).list();
		List<Metric> metrics = new ArrayList<Metric>();
		for(MetricEntity entity : entityList){
			metrics.add(entity.getMetric());
		}
		return metrics;
	}

}
