package com.cante.metrics.entity.pojo;

import lombok.Data;

@Data
public class SearchParameters {
	private String applicationName;
	private String hostName;
	private String operation;
	private String marketplace;
	private String metricName;
	private Long startTime;
	private Long endTime;
}
