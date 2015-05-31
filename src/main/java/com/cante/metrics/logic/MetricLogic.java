package com.cante.metrics.logic;

import java.util.List;

import lombok.Setter;

import com.cante.metrics.dao.MetricDao;
import com.cante.metrics.entity.pojo.Metric;

public class MetricLogic {
	@Setter private MetricDao metricDao;
	
	public List<Metric> getMetrics(){
		return metricDao.getAllMetrics();
		
	}
}
