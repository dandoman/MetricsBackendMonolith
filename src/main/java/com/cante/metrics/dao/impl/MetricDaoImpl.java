package com.cante.metrics.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Setter;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.cante.metrics.dao.MetricDao;
import com.cante.metrics.entity.MetricEntity;
import com.cante.metrics.entity.StagedMetricEntity;
import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.entity.pojo.SearchParameters;

public class MetricDaoImpl implements MetricDao {
	@Setter private SessionFactory sessionFactory;

	public void create(String ownerId, MetricEntity m) {
		m.setId(UUID.randomUUID().toString());
		m.setOwnerId(ownerId);
		m.setCreationDate(new Date());
		sessionFactory.getCurrentSession().save(m);
	}
	
	public List<Metric> getAllMetrics(){
		List<MetricEntity> entityList = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM metrics").addEntity(MetricEntity.class).list();
		List<Metric> metrics = new ArrayList<Metric>();
		for(MetricEntity entity : entityList){
			metrics.add(entity.getMetric());
		}
		return metrics;
	}

	public List<Metric> search(SearchParameters sp, String customerId) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(MetricEntity.class);
		if(!StringUtils.isEmpty(sp.getApplicationName())) {
			c.add(Restrictions.eq("applicationName", sp.getApplicationName()));
		}
		if(!StringUtils.isEmpty(sp.getHostName())) {
			c.add(Restrictions.eq("hostName", sp.getHostName()));
		}
		if(!StringUtils.isEmpty(sp.getMarketplace())) {
			c.add(Restrictions.eq("marketplace", sp.getMarketplace()));
		}
		if(!StringUtils.isEmpty(sp.getOperation())) {
			c.add(Restrictions.eq("operation", sp.getOperation()));
		}
		if(!StringUtils.isEmpty(sp.getMetricName())) {
			c.add(Restrictions.eq("metricName", sp.getMetricName()));
		}
		if(sp.getStartTime() != null) {
			c.add(Restrictions.ge("timeStamp", sp.getStartTime()));
		}
		if(sp.getEndTime() != null) {
			c.add(Restrictions.le("timeStamp", sp.getEndTime()));
		}
		c.add(Restrictions.eq("ownerId", customerId));
		
		List<MetricEntity> result = c.list();
		if(result == null){
			return new ArrayList<Metric>();
		}
		else{
			ArrayList<Metric> metrics = new ArrayList<Metric>();
			for(MetricEntity me : result) {
				metrics.add(me.getMetric());
			}
			return metrics;
		}
	}

}
