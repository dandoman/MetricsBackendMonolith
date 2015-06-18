package com.cante.metrics.dao.impl;

import java.util.Date;
import java.util.UUID;

import lombok.Setter;

import org.hibernate.SessionFactory;

import com.cante.metrics.dao.MonitorDao;
import com.cante.metrics.entity.MonitorEntity;
import com.cante.metrics.entity.pojo.Monitor;

public class MonitorDaoImpl implements MonitorDao{
	@Setter private SessionFactory sessionFactory;
	
	public void create(Monitor m, String ownerId) {
		MonitorEntity me = new MonitorEntity();
		me.setId(UUID.randomUUID().toString());
		me.setApplicationName(m.getApplicationName());
		me.setCounts(m.getCounts());
		me.setCurrentBreaches(0);
		me.setHostName(m.getHostName());
		me.setLastFired(null);
		me.setLastUpdated(new Date());
		me.setLess(m.getLess());
		me.setMarketplace(m.getMarketplace());
		me.setMetricName(m.getMetricName());
		me.setOperation(m.getOperation());
		me.setOwnerId(ownerId);
		me.setRecordVersionNumber(0);
		me.setThreshold(m.getThreshold());
		me.setType(m.getType());
		me.setName(m.getName());
		me.setDescription(m.getDescription());
		sessionFactory.getCurrentSession().save(me);
	}

}
