package com.cante.metrics.dao;

import java.util.Date;
import java.util.List;

import com.cante.metrics.entity.StagedMetricEntity;
import com.cante.metrics.entity.pojo.StagedMetric;
import com.cante.metrics.entity.pojo.StagedStatus;

public interface StagedMetricDao {
	
	public void create(String ownerId, StagedMetric m);
	public List<StagedMetricEntity> getAllStagedMetrics();
	public List<StagedMetricEntity> getStagedMetricsByOwnerAndRange(String id,
			Date start, Date end, StagedStatus created);
	public void setStatus(StagedMetricEntity e, StagedStatus processed5Min);
}
