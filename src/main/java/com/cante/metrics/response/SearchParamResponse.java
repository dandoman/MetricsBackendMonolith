package com.cante.metrics.response;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cante.metrics.entity.pojo.MetricHeader;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchParamResponse {
	private List<MetricHeader> tuples;
	private List<String> applicationNames;
	private List<String> hostnames;
	private List<String> operationNames;
	private List<String> marketplaces;
	private List<String> metricNames;
}
