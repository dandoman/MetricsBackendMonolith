package com.cante.metrics.entity.pojo;

import lombok.Data;

@Data
public class Monitor {
	private String id;
	private String name;
	private String description;
	
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private String metricName;
	private String type;
	private Double threshold;
	private Integer counts;
	private Boolean less; //Fix later, ugly as fuuckk
	private String emailRecipient;
	private Integer currentBreaches;
}
