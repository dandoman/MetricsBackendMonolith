package com.cante.metrics.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import com.cante.metrics.dao.StagedMetricDao;
import com.cante.metrics.entity.StagedMetricEntity;
import com.cante.metrics.entity.pojo.StagedMetric;
import com.cante.metrics.entity.pojo.StagedStatus;

@Log4j
public class StagedMetricDaoImpl implements StagedMetricDao {
	@Setter
	private SessionFactory sessionFactory;

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

	public List<StagedMetricEntity> getAllStagedMetrics() {
		List<StagedMetricEntity> entityList = sessionFactory
				.getCurrentSession()
				.createSQLQuery("SELECT * FROM staged_metrics")
				.addEntity(StagedMetricEntity.class).list();
		List<StagedMetric> metrics = new ArrayList<StagedMetric>();
		for (StagedMetricEntity entity : entityList) {
			metrics.add(entity.getStagedMetric());
		}
		return entityList;
	}

	public List<StagedMetricEntity> getStagedMetricsByOwnerAndRange(String id,
			Date start, Date end, StagedStatus status) {

		Criteria c = sessionFactory.getCurrentSession().createCriteria(StagedMetricEntity.class);
		c.add(Restrictions.eq("ownerId", id));
		c.add(Restrictions.eq("status", status));
		c.add(Restrictions.ge("timestamp", start.getTime()));
		c.add(Restrictions.le("timestamp", end.getTime()));
		
		List<StagedMetricEntity> result = c.list();
		if(result == null){
			return new ArrayList<StagedMetricEntity>();
		}
		else{
			return result;
		}

	}

	public void setStatus(StagedMetricEntity e, StagedStatus status) {
		e.setStatus(status);
		e.setLastUpdatedDate(new Date());
		sessionFactory.getCurrentSession().saveOrUpdate(e);
	}

}
