package com.cante.metrics.dao;

import com.cante.metrics.entity.pojo.StagedMetric;

public interface StagedMetricDao {
	
	public void create(String ownerId, StagedMetric m);

}
