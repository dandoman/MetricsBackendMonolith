package com.cante.metrics.response;

import java.util.List;

import com.cante.metrics.entity.StagedMetric;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BulkUploadResponse extends Response {
	private List<StagedMetric> failedMetrics;
}
