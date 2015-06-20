package com.cante.metrics.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Setter;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.cante.metrics.dao.MonitorDao;
import com.cante.metrics.entity.MetricEntity;
import com.cante.metrics.entity.MonitorEntity;
import com.cante.metrics.entity.pojo.Monitor;
import com.cante.metrics.entity.pojo.MonitorSearchParam;

public class MonitorDaoImpl implements MonitorDao{
	@Setter private SessionFactory sessionFactory;
	
	public Monitor create(Monitor m, String ownerId) {
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
		me.setThreshold(m.getThreshold());
		me.setType(m.getType());
		me.setName(m.getName());
		me.setDescription(m.getDescription());
		me.setEmailRecipient(m.getEmailRecipient());
		sessionFactory.getCurrentSession().save(me);
		
		return me.getMonitor();
	}
	
	public List<Monitor> queryMonitors(MonitorSearchParam p){
		Criteria c = sessionFactory.getCurrentSession().createCriteria(MonitorEntity.class);
		c.add(Restrictions.eq("ownerId", p.getOwnerId()));
		List<MonitorEntity> monitorEntities = c.list();
		
		List<Monitor> monitors = new ArrayList<Monitor>();
		for(MonitorEntity me : monitorEntities){
			monitors.add(me.getMonitor());
		}
		
		return monitors;
	}

}
