package com.cante.metrics.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cante.metrics.entity.MetricEntity;
import com.cante.metrics.entity.StagedMetricEntity;
import com.cante.metrics.entity.pojo.Metric;

public class CrunchingLogic {

	public List<StagedMetricEntity> aggregate(List<StagedMetricEntity> stagedMetrics, boolean appName,boolean operation,boolean marketplace,boolean hostname) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public double computeP0(List<StagedMetricEntity> stagedMetrics){
		return 0;
	}
	
	public double computeP50(List<StagedMetricEntity> stagedMetrics){
		return 0;
	}
	
	public double computeP75(List<StagedMetricEntity> stagedMetrics){
		return 0;
	}

	public double computeP90(List<StagedMetricEntity> stagedMetrics){
		return 0;
	}
	
	public double computeP99(List<StagedMetricEntity> stagedMetrics){
		return 0;
	}
	
	public double computeP999(List<StagedMetricEntity> stagedMetrics){
		return 0;
	}
	
	public double computeP9999(List<StagedMetricEntity> stagedMetrics){
		return 0;
	}
	
	public double computeP100(List<StagedMetricEntity> stagedMetrics){
		return 0;
	}
	
	public double computeAvg(List<StagedMetricEntity> stagedMetrics){
		return 0;
	}
	
	public double computeCount(List<StagedMetricEntity> stagedMetrics){
		return 0;
	}
	
	public double computeSum(List<StagedMetricEntity> stagedMetrics){
		return 0;
	}


	public MetricEntity computeMetricRow(List<StagedMetricEntity> metrics,StagedMetricEntity sample) {
		MetricEntity metricEntity = new MetricEntity();
		metricEntity.setApplicationName(sample.getApplicationName());
		metricEntity.setHostName(sample.getHostName());
		metricEntity.setMarketplace(sample.getMarketplace());
		metricEntity.setMetricName(sample.getMetricName());
		metricEntity.setOperation(sample.getOperation());
		metricEntity.setOwnerId(sample.getOwnerId());
		
		//Sort metrics first, makes it easier
		Collections.sort(metrics,new Comparator<StagedMetricEntity>() {
			public int compare(StagedMetricEntity o1, StagedMetricEntity o2) {
				if(o1.getValue() < o2.getValue()){
					return -1;
				}
				if(o1.getValue() == o2.getValue()){
					return 0;
				}
				
				return 1;
			}
		});
		
		metricEntity.setAvg(computeAvg(metrics));
		metricEntity.setCount(computeCount(metrics));
		metricEntity.setSum(computeSum(metrics));
		metricEntity.setP0(computeP0(metrics));
		metricEntity.setP50(computeP50(metrics));
		metricEntity.setP75(computeP75(metrics));
		metricEntity.setP90(computeP90(metrics));
		metricEntity.setP99(computeP99(metrics));
		metricEntity.setP999(computeP999(metrics));
		metricEntity.setP9999(computeP9999(metrics));
		metricEntity.setP100(computeP100(metrics));
		
		return metricEntity;
	}
}
