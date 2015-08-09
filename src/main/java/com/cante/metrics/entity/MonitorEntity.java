package com.cante.metrics.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cante.metrics.entity.pojo.Monitor;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "monitors")
@NoArgsConstructor
public class MonitorEntity {
	@Id
	private String id;
	private String ownerId;
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private String metricName;
	private String type;
	private double threshold;
	private int counts;
	private boolean less;
	
	private Date lastFired;
	private int currentBreaches;
	private Date lastUpdated;
	
	private String name;
	private String description;
	private String emailRecipient;
	private int recordVersionNumber;
	
	public Monitor getMonitor() {
		Monitor m = new Monitor();
		m.setApplicationName(this.applicationName);
		m.setCounts(this.counts);
		m.setDescription(this.description);
		m.setEmailRecipient(this.emailRecipient);
		m.setHostName(this.hostName);
		m.setId(this.id);
		m.setLess(this.less);
		m.setMarketplace(this.marketplace);
		m.setMetricName(this.metricName);
		m.setName(this.name);
		m.setOperation(this.operation);
		m.setThreshold(this.threshold);
		m.setType(this.type); 
		m.setCurrentBreaches(this.currentBreaches);
		return m;
	}
}
