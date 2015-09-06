package com.cante.metrics.dao;

import java.util.List;

import com.cante.metrics.entity.MetricEntity;
import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.entity.pojo.MetricHeader;
import com.cante.metrics.entity.pojo.SearchParameters;
import com.cante.metrics.response.MetricParamsResponse;

public interface MetricDao {
	public void create(String ownerId, MetricEntity m);
	public List<Metric> getAllMetrics();
	public List<Metric> search(SearchParameters sp, String customerId);
	public List<MetricHeader> getMetricHeaders(String param, String customerId, long startTime, long endTime);
}
