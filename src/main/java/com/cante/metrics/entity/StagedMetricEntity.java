package com.cante.metrics.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import com.cante.metrics.entity.pojo.StagedMetric;

@Data
@Entity
@Table(name = "staged_metrics")
public class StagedMetricEntity {

	@Id private String id;
	private String ownerId;
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private long startTime;
	private long endTime;
	private String metricName;
	private double value;
	private Date creationDate;

	public StagedMetricEntity(){
		
	}
	
	public StagedMetricEntity(StagedMetric m) {
		this.applicationName = m.getApplicationName();
		this.operation = m.getOperation();
		this.marketplace = m.getMarketplace();
		this.hostName = m.getHostName();
		this.startTime = m.getStartTime();
		this.endTime = m.getEndTime();
		this.metricName = m.getMetricName();
		this.value = m.getValue();
	}

	public StagedMetric getStagedMetric() {
		StagedMetric m = new StagedMetric();
		m.setApplicationName(applicationName);
		m.setEndTime(endTime);
		m.setHostName(hostName);
		m.setMarketplace(marketplace);
		m.setMetricName(metricName);
		m.setOperation(operation);
		m.setStartTime(startTime);
		m.setValue(value);
		return m;
	}
}
