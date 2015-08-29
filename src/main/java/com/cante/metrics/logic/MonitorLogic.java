package com.cante.metrics.logic;

import java.util.List;

import lombok.Setter;

import org.springframework.util.StringUtils;

import com.cante.metrics.dao.MonitorDao;
import com.cante.metrics.entity.pojo.Monitor;
import com.cante.metrics.entity.pojo.MonitorSearchParam;
import com.cante.metrics.exception.BadArgsException;

public class MonitorLogic {
	
	@Setter private MonitorDao dao;

	public Monitor createMonitor(Monitor m, String ownerId) {
		if(StringUtils.isEmpty(m.getMetricName())){
			throw new BadArgsException("Must specify a metric name");
		}
		
		if(StringUtils.isEmpty(m.getType())){
			throw new BadArgsException("Must specify the type of metric");
		}
		
		if(m.getThreshold() == null){
			throw new BadArgsException("Must specify the threshold");
		}
		
		if(m.getLess() == null){
			throw new BadArgsException("Must specify whether to alarm at less than the threshold or above the threshold");
		}
		
		if(StringUtils.isEmpty(m.getApplicationName())){
			m.setApplicationName("ALL");
		}
		
		if(StringUtils.isEmpty(m.getHostName())){
			m.setHostName("ALL");
		}
		
		if(StringUtils.isEmpty(m.getMarketplace())){
			m.setMarketplace("ALL");
		}
		
		if(StringUtils.isEmpty(m.getOperation())){
			m.setOperation("ALL");
		}
		
		return dao.create(m,ownerId);
	}

	public List<Monitor> queryMonitors(MonitorSearchParam p) {
		if(StringUtils.isEmpty(p.getOwnerId())){
			throw new BadArgsException("Monitor owner id required for monitor query");
		}
		
		return dao.queryMonitors(p);
	}

	public void fireMonitor(Monitor m) {
		dao.fireMonitor(m);
	}
	
	public void resetMonitor(Monitor m) {
		dao.resetMonitor(m);
	}
	
}
