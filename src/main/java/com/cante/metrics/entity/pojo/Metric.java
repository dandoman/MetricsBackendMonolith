package com.cante.metrics.entity.pojo;

import lombok.Data;

@Data
public class Metric {
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private long timeStamp;
	private Aggregation aggregation;
	private TimeLevel timeLevel;
	private String metricName;
	private double value;
}
