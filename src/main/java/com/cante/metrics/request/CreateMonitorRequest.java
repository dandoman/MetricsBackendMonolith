package com.cante.metrics.request;

import lombok.Data;

@Data
public class CreateMonitorRequest {
	private String customerId;
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private String metricName;
	private String type;
	private Double threshold;
	private Integer counts;
	private Boolean less;
	
	private String description;
	private String emailRecipient;
}
