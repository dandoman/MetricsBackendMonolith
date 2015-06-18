package com.cante.metrics.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private int recordVersionNumber;
	
	private String name;
	private String description;
	
}
