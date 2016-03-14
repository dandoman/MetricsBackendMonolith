package com.cante.metrics.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.cante.metrics.entity.pojo.StagedMetric;
import com.cante.metrics.entity.pojo.StagedStatus;

@Data
@Entity
@Table(name = "staged_metrics")
@NoArgsConstructor
public class StagedMetricEntity {

	@Id private String id;
	private String ownerId;
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private long timestamp;
	private String metricName;
	private double value;
	private Date creationDate;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	private StagedStatus status;
	
	public StagedMetricEntity(StagedMetric m) {
		this.applicationName = m.getApplicationName();
		this.operation = m.getOperation();
		this.marketplace = m.getMarketplace();
		this.hostName = m.getHostName();
		this.timestamp = m.getTimeStamp();
		this.metricName = m.getMetricName();
		this.value = m.getValue();
	}

	public StagedMetric getStagedMetric() {
		StagedMetric m = new StagedMetric();
		m.setApplicationName(applicationName);
		m.setTimeStamp(timestamp);
		m.setHostName(hostName);
		m.setMarketplace(marketplace);
		m.setMetricName(metricName);
		m.setOperation(operation);
		m.setValue(value);
		return m;
	}
}
