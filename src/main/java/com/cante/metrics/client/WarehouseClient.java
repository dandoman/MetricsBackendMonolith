package com.cante.metrics.client;

import java.util.List;

import com.cante.metrics.entity.pojo.StagedMetric;

public interface WarehouseClient {
	public void warehouseMetrics(List<StagedMetric> metrics);
}
