package com.cante.metrics.request;

import java.util.List;

import com.cante.metrics.entity.StagedMetric;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BulkUploadRequest extends Request {
	private List<StagedMetric> metrics;
}
