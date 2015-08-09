package com.cante.metrics.request;

import java.util.List;

import com.cante.metrics.entity.pojo.StagedMetric;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class BulkUploadRequest {
	private String apiKey;
	private List<StagedMetric> metrics;
}
