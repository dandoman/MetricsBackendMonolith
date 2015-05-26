package com.cante.metrics.dao;

import java.util.List;

import com.cante.metrics.entity.pojo.Metric;

public interface MetricDao {
	public void create(String ownerId, Metric m);
	public List<Metric> getAllMetrics();
}
