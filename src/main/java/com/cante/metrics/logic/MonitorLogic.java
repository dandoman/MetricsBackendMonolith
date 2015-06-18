package com.cante.metrics.logic;

import lombok.Setter;

import org.springframework.util.StringUtils;

import com.cante.metrics.dao.MonitorDao;
import com.cante.metrics.entity.pojo.Monitor;
import com.cante.metrics.exception.BadArgsException;

public class MonitorLogic {
	
	@Setter private MonitorDao dao;

	public void createMonitor(Monitor m, String apiKey) {
		//get owner from api key, if nt found, 400
		String ownerId = "123123";
		
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
		
		dao.create(m,ownerId);
	}
	
}
