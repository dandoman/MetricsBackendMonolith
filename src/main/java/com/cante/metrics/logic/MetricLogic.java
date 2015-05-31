package com.cante.metrics.logic;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import com.cante.metrics.dao.MetricDao;
import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.entity.pojo.SearchParameters;

public class MetricLogic {
	@Setter
	private MetricDao metricDao;

	public List<Metric> getMetrics() {
		return metricDao.getAllMetrics();

	}

	public List<Metric> search(SearchParameters sp) {
		if (sp.getApplicationName() == null && sp.getHostName() == null
				&& sp.getMarketplace() == null && sp.getMetricName() == null
				&& sp.getStartTime() == null && sp.getEndTime() == null
				&& sp.getOperation() == null) {
			return new ArrayList<Metric>();
		}
		return metricDao.search(sp);
	}
}
