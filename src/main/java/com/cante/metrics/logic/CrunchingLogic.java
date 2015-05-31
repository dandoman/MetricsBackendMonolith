package com.cante.metrics.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.javatuples.Tuple;
import org.javatuples.Unit;

import com.cante.metrics.entity.MetricEntity;
import com.cante.metrics.entity.StagedMetricEntity;
import com.cante.metrics.entity.pojo.Metric;

public class CrunchingLogic {

	public List<List<StagedMetricEntity>> aggregate(
			List<StagedMetricEntity> stagedMetrics, boolean appName,
			boolean operation, boolean marketplace, boolean hostname) {

		Map<Tuple,List<StagedMetricEntity>> aggregation = new HashMap<Tuple, List<StagedMetricEntity>>();

		for(StagedMetricEntity m : stagedMetrics){
			TupleBuilder builder = new TupleBuilder();
			
			//If we aren't aggregating by the field, set it to "ALL"
			if(appName){
				builder.addValue(m.getApplicationName());
			}
			
			if(operation){
				builder.addValue(m.getOperation());
			}
			
			if(marketplace){
				builder.addValue(m.getMarketplace());
			}
			
			if(hostname){
				builder.addValue(m.getHostName());
			}
			
			builder.addValue(m.getMetricName());
			
			Tuple tuple = builder.build();
			
			List<StagedMetricEntity> storedTupleMetrics = aggregation.get(tuple);
			if(storedTupleMetrics == null){
				List<StagedMetricEntity> newList = new ArrayList<StagedMetricEntity>();
				newList.add(m);
				aggregation.put(tuple, newList);
			}
			else{
				storedTupleMetrics.add(m);
			}
			
		}
		
		List<List<StagedMetricEntity>> aggregatedMetrics = new ArrayList<List<StagedMetricEntity>>();
		for(List<StagedMetricEntity> list : aggregation.values()){
			aggregatedMetrics.add(list);
		}
		
		return aggregatedMetrics;
		
	}

	public double computeP0(List<StagedMetricEntity> stagedMetrics) {
		return stagedMetrics.get(0).getValue();
	}

	public double computeP50(List<StagedMetricEntity> stagedMetrics) {
		double index = 0.50*(stagedMetrics.size() - 1);
		int intIndex = (int) Math.ceil(index);
		if(intIndex > (stagedMetrics.size() - 1)){
			return stagedMetrics.get(stagedMetrics.size() - 1).getValue();
		}
		return stagedMetrics.get(intIndex).getValue();
	}

	public double computeP75(List<StagedMetricEntity> stagedMetrics) {
		double index = 0.75*(stagedMetrics.size() - 1);
		int intIndex = (int) Math.ceil(index);
		if(intIndex > (stagedMetrics.size() - 1)){
			return stagedMetrics.get(stagedMetrics.size() - 1).getValue();
		}
		return stagedMetrics.get(intIndex).getValue();
	}

	public double computeP90(List<StagedMetricEntity> stagedMetrics) {
		double index = 0.90*(stagedMetrics.size() - 1);
		int intIndex = (int) Math.ceil(index);
		if(intIndex > (stagedMetrics.size() - 1)){
			return stagedMetrics.get(stagedMetrics.size() - 1).getValue();
		}
		return stagedMetrics.get(intIndex).getValue();
	}

	public double computeP99(List<StagedMetricEntity> stagedMetrics) {
		double index = 0.99*(stagedMetrics.size() - 1);
		int intIndex = (int) Math.ceil(index);
		if(intIndex > (stagedMetrics.size() - 1)){
			return stagedMetrics.get(stagedMetrics.size() - 1).getValue();
		}
		return stagedMetrics.get(intIndex).getValue();
	}

	public double computeP999(List<StagedMetricEntity> stagedMetrics) {
		double index = 0.999*(stagedMetrics.size() - 1);
		int intIndex = (int) Math.ceil(index);
		if(intIndex > (stagedMetrics.size() - 1)){
			return stagedMetrics.get(stagedMetrics.size() - 1).getValue();
		}
		return stagedMetrics.get(intIndex).getValue();
	}

	public double computeP9999(List<StagedMetricEntity> stagedMetrics) {
		double index = 0.9999*(stagedMetrics.size() - 1);
		int intIndex = (int) Math.ceil(index);
		if(intIndex > (stagedMetrics.size() - 1)){
			return stagedMetrics.get(stagedMetrics.size() - 1).getValue();
		}
		return stagedMetrics.get(intIndex).getValue();
	}

	public double computeP100(List<StagedMetricEntity> stagedMetrics) {
		return stagedMetrics.get(stagedMetrics.size() - 1).getValue();
	}

	public double computeAvg(List<StagedMetricEntity> stagedMetrics) {
		double sum = 0;
		for(StagedMetricEntity m : stagedMetrics){
			sum = sum + m.getValue();
		}
		
		return sum/stagedMetrics.size();
	}

	public double computeCount(List<StagedMetricEntity> stagedMetrics) {
		return stagedMetrics.size();
	}

	public double computeSum(List<StagedMetricEntity> stagedMetrics) {
		double sum = 0;
		for(StagedMetricEntity m : stagedMetrics){
			sum = sum + m.getValue();
		}
		return sum;
	}

	public MetricEntity computeMetricRow(List<StagedMetricEntity> metrics,
			StagedMetricEntity sample) {
		
		MetricEntity metricEntity = new MetricEntity();
		metricEntity.setApplicationName(sample.getApplicationName());
		metricEntity.setHostName(sample.getHostName());
		metricEntity.setMarketplace(sample.getMarketplace());
		metricEntity.setMetricName(sample.getMetricName());
		metricEntity.setOperation(sample.getOperation());
		metricEntity.setOwnerId(sample.getOwnerId());

		// Sort metrics first, makes it easier
		Collections.sort(metrics, new Comparator<StagedMetricEntity>() {
			public int compare(StagedMetricEntity o1, StagedMetricEntity o2) {
				if (o1.getValue() < o2.getValue()) {
					return -1;
				}
				if (o1.getValue() == o2.getValue()) {
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
