package com.cante.metrics.logic;

import java.util.ArrayList;
import java.util.List;

import com.cante.metrics.entity.StagedMetricEntity;
import com.cante.metrics.entity.pojo.Metric;

public class CrunchingLogic {

	public List<StagedMetricEntity> aggregate(List<StagedMetricEntity> stagedMetrics, boolean appName,boolean operation,boolean marketplace,boolean hostname) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Metric> computeAll(List<StagedMetricEntity> stagedMetrics){
		List<Metric> computedMetrics = new ArrayList<Metric>();
		computedMetrics.addAll(computeP0(stagedMetrics));
		computedMetrics.addAll(computeP50(stagedMetrics));
		computedMetrics.addAll(computeP75(stagedMetrics));
		computedMetrics.addAll(computeP90(stagedMetrics));
		computedMetrics.addAll(computeP99(stagedMetrics));
		computedMetrics.addAll(computeP999(stagedMetrics));
		computedMetrics.addAll(computeP9999(stagedMetrics));
		computedMetrics.addAll(computeP100(stagedMetrics));
		computedMetrics.addAll(computeAvg(stagedMetrics));
		computedMetrics.addAll(computeCount(stagedMetrics));
		computedMetrics.addAll(computeSum(stagedMetrics));
		
		return computedMetrics;
	}
	
	private List<Metric> computeP0(List<StagedMetricEntity> stagedMetrics){
		return null;
	}
	
	private List<Metric> computeP50(List<StagedMetricEntity> stagedMetrics){
		return null;
	}
	
	private List<Metric> computeP75(List<StagedMetricEntity> stagedMetrics){
		return null;
	}

	private List<Metric> computeP90(List<StagedMetricEntity> stagedMetrics){
		return null;
	}
	
	private List<Metric> computeP99(List<StagedMetricEntity> stagedMetrics){
		return null;
	}
	
	private List<Metric> computeP999(List<StagedMetricEntity> stagedMetrics){
		return null;
	}
	
	private List<Metric> computeP9999(List<StagedMetricEntity> stagedMetrics){
		return null;
	}
	
	private List<Metric> computeP100(List<StagedMetricEntity> stagedMetrics){
		return null;
	}
	
	private List<Metric> computeAvg(List<StagedMetricEntity> stagedMetrics){
		return null;
	}
	
	private List<Metric> computeCount(List<StagedMetricEntity> stagedMetrics){
		return null;
	}
	
	private List<Metric> computeSum(List<StagedMetricEntity> stagedMetrics){
		return null;
	}
}
