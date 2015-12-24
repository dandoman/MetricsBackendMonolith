package com.cante.metrics.logic;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import com.cante.metrics.dao.StagedMetricDao;
import com.cante.metrics.entity.StagedMetricEntity;
import com.cante.metrics.entity.pojo.Customer;
import com.cante.metrics.entity.pojo.StagedMetric;

@Log4j
public class StagedMetricLogic {
	@Setter private StagedMetricDao dao;
	@Setter private CustomerLogic customerLogic;
	public List<StagedMetric> stageMetrics(String apiKey, List<StagedMetric> metrics) {
		List<StagedMetric> failures = new ArrayList<StagedMetric>();
		//getOwnerForAPIKey
		Customer customer = customerLogic.getCustomerForAPIKey(apiKey);
		if(customer == null){
			return metrics;
		}
		String ownerId = customer.getId();
		
		for(StagedMetric m : metrics){
			try{
				dao.create(ownerId, m);
			} catch(Exception e){
				log.error("Error when staging metric",e);
				failures.add(m);
			}
		}
		
		return failures;
	}

	public List<StagedMetricEntity> getStagedMetrics() {
		return dao.getAllStagedMetrics();
	}
}
