package com.cante.metrics.dao;

import java.util.List;

import com.cante.metrics.entity.MetricEntity;
import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.entity.pojo.SearchParameters;

public interface MetricDao {
	public void create(String ownerId, MetricEntity m);
	public List<Metric> getAllMetrics();
	public List<Metric> search(SearchParameters sp);
}
