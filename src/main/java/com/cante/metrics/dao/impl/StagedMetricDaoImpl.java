package com.cante.metrics.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import com.cante.metrics.dao.StagedMetricDao;
import com.cante.metrics.entity.StagedMetricEntity;
import com.cante.metrics.entity.pojo.StagedMetric;
import com.cante.metrics.entity.pojo.StagedStatus;

@Log4j
public class StagedMetricDaoImpl implements StagedMetricDao {
	@Setter private SessionFactory sessionFactory;
	
	public void create(String ownerId, StagedMetric m) {
		StagedMetricEntity metric = new StagedMetricEntity(m);
		metric.setId(UUID.randomUUID().toString());
		metric.setOwnerId(ownerId);
		Date now = new Date();
		metric.setCreationDate(now);
		metric.setLastUpdatedDate(now);
		metric.setStatus(StagedStatus.CREATED);
		
		sessionFactory.getCurrentSession().save(metric);
	}
	
	public List<StagedMetric> getAllStagedMetrics(){
		List<StagedMetricEntity> entityList = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM staged_metrics").addEntity(StagedMetricEntity.class).list();
		List<StagedMetric> metrics = new ArrayList<StagedMetric>();
		for(StagedMetricEntity entity : entityList){
			metrics.add(entity.getStagedMetric());
		}
		return metrics;
	}

	public List<StagedMetricEntity> getStagedMetricsByOwnerAndRange(String id,
			Date start, Date end) {
		// TODO Auto-generated method stub
		return new ArrayList<StagedMetricEntity>();
	}

}
