package com.cante.metrics.dao;

import java.util.List;

import com.cante.metrics.entity.pojo.StagedMetric;

public interface StagedMetricDao {
	
	public void create(String ownerId, StagedMetric m);
	public List<StagedMetric> getAllStagedMetrics();
}
