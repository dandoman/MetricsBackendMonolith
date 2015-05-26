package com.cante.metrics.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cante.metrics.entity.pojo.Aggregation;
import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.entity.pojo.TimeLevel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "metrics")
@NoArgsConstructor
public class MetricEntity {
	public MetricEntity(Metric m) {
		this.applicationName = m.getApplicationName();
		this.operation = m.getOperation();
		this.marketplace = m.getMarketplace();
		this.hostName = m.getHostName();
		this.metricName = m.getMetricName();
		this.value = m.getValue();
		this.timeLevel = m.getTimeLevel();
		this.aggregation = m.getAggregation();
		this.timeStamp = m.getTimeStamp();
	}

	@Id
	private String id;
	private String ownerId;
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private long timeStamp;
	private Aggregation aggregation;
	private TimeLevel timeLevel;
	private String metricName;
	private double value;
	private Date creationDate;
	
	public Metric getMetric() {
		Metric m = new Metric();
		m.setAggregation(this.aggregation);
		m.setApplicationName(this.applicationName);
		m.setHostName(this.hostName);
		m.setMarketplace(this.marketplace);
		m.setMetricName(this.metricName);
		m.setOperation(this.operation);
		m.setTimeLevel(this.timeLevel);
		m.setTimeStamp(this.timeStamp);
		m.setValue(this.value);
		return m;
	}
}
