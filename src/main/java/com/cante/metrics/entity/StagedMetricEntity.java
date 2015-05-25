package com.cante.metrics.entity;

import lombok.Data;

import com.cante.metrics.entity.pojo.StagedMetric;

@Data
public class StagedMetricEntity {
	
	private String ownerId;
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private long startTime;
	private long endTime;
	private String metricName;
	private double value;
	
	public StagedMetricEntity(StagedMetric m){
		
	}
	
	public StagedMetric getStagedMetric(){
		return new StagedMetric();
	}
}
