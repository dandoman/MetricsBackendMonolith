package com.cante.metrics.logic;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import com.cante.metrics.dao.StagedMetricDao;
import com.cante.metrics.entity.StagedMetric;

@Log4j
public class StagedMetricLogic {
	
	@Setter StagedMetricDao dao;

	public List<StagedMetric> stageMetrics(List<StagedMetric> metrics) {
		List<StagedMetric> failures = new ArrayList<StagedMetric>();
		for(StagedMetric m : metrics){
			try{
				dao.create(m);
			} catch(Exception e){
				log.error("Error when staging metric",e);
				failures.add(m);
			}
		}
		
		return failures;
		
	}

}
