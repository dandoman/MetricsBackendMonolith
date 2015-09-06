package com.cante.metrics.entity.pojo;

import lombok.Data;

@Data
public class MetricHeader {
	private String applicationName;
	private String hostname;
	private String marketplace;
	private String operation;
	private String metricName;
}
