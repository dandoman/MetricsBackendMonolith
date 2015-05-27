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

	@Id
	private String id;
	private String ownerId;
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private long timeStamp;
	private TimeLevel timeLevel;
	private String metricName;
	private Date creationDate;
	private double p0;
	private double p50;
	private double p75;
	private double p90;
	private double p99;
	private double p999;
	private double p9999;
	private double p100;
	private double avg;
	private double count;
	private double sum;
	
	public Metric getMetric() {
		Metric m = new Metric();
		m.setApplicationName(this.applicationName);
		m.setHostName(this.hostName);
		m.setMarketplace(this.marketplace);
		m.setMetricName(this.metricName);
		m.setOperation(this.operation);
		m.setTimeLevel(this.timeLevel);
		m.setTimeStamp(this.timeStamp);
		
		m.setP0(this.p0);
		m.setP50(this.p50);
		m.setP75(this.p75);
		m.setP90(this.p90);
		m.setP99(this.p99);
		m.setP999(this.p999);
		m.setP9999(this.p9999);
		m.setP100(this.p100);
		m.setAvg(this.avg);
		m.setCount(this.count);
		m.setSum(this.sum);
		return m;
	}
}
