package com.cante.metrics.timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import com.cante.metrics.dao.MetricDao;
import com.cante.metrics.dao.StagedMetricDao;
import com.cante.metrics.entity.StagedMetricEntity;
import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.logic.CrunchingLogic;
import com.cante.metrics.logic.LockManagementLogic;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class MetricsCruncher {
	@Setter
	private String selfHostId;
	@Setter
	private StagedMetricDao stagedDao;
	@Setter
	private MetricDao metricDao;
	@Setter
	private CrunchingLogic crunchingLogic;
	@Setter
	private LockManagementLogic lockLogic;

	public void init(){
		selfHostId = selfHostId + "-" + UUID.randomUUID().toString();
	}
	
	@Transactional
	public void fiveMinCrunch() {
		log.info("Running 5 minute crunching task");
		List<String> ownerIds = lockLogic
				.getOwnerIdsForResponsibility(selfHostId);
		
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MINUTE, -10);
		Date end = cal.getTime();
		cal.setTime(now);
		cal.add(Calendar.MINUTE, -15);
		Date start = cal.getTime();
		
		for (String id : ownerIds) {
			List<StagedMetricEntity> stagedMetrics = stagedDao
					.getStagedMetricsByOwnerAndRange(id, start,end);
			
			//Aggregate by all subsets of appName, operation, marketplace,hostname
			List<Metric> aggregatedMetrics = new ArrayList<Metric>();
			
			//All,All,All,All,metric,value
			crunchingLogic.aggregate(stagedMetrics,false,false,false,false); //100%
			
			//App,All,All,All,metric,value
			crunchingLogic.aggregate(stagedMetrics,true,false,false,false); //75%
			
			//All,operation,All,All,metric,value
			crunchingLogic.aggregate(stagedMetrics,false,true,false,false); //75%
			
			//All,All,marketplace,All,metric,value
			crunchingLogic.aggregate(stagedMetrics,false,false,true,false); //75%
			
			//All,All,All,hostname,metric,value
			crunchingLogic.aggregate(stagedMetrics,false,false,false,true); //75%
			
			//App,operation,All,All,metric,value
			crunchingLogic.aggregate(stagedMetrics,true,true,false,false); //50%
			
			//App,All,marketplace,All,metric,value
			crunchingLogic.aggregate(stagedMetrics,true,false,true,false); //50%
			
			//App,All,All,hostname,metric,value
			crunchingLogic.aggregate(stagedMetrics,true,false,false,true); //50%
			
			//All,operation,marketplace,All,metric,value
			crunchingLogic.aggregate(stagedMetrics,false,true,true,false); //50%
			
			//All,operation,All,hostname,metric,value
			crunchingLogic.aggregate(stagedMetrics,false,true,false,true); //50%
			
			//All,All,marketplace,hostname,metric,value
			crunchingLogic.aggregate(stagedMetrics,false,false,true,true); //50%
			
			//App,operation,marketplace,All,metric,value
			crunchingLogic.aggregate(stagedMetrics,true,true,true,false); //25%
			
			//App,operation,All,hostname,metric,value
			crunchingLogic.aggregate(stagedMetrics,true,true,false,true); //25%
			
			//App,All,marketplace,hostname,metric,value
			crunchingLogic.aggregate(stagedMetrics,true,false,true,true); //25%
			
			//All,operation,marketplace,hostname,metric,value
			crunchingLogic.aggregate(stagedMetrics,false,true,true,true); //25%
			
			//App,operation,marketplace,hostname,metric,value
			crunchingLogic.aggregate(stagedMetrics,true,true,true,true); //5%
			
			//Total of 800% assuming even distribution, but it wont be that, it will be less, maybe closer to 500%
			
		}
	}

	@Transactional
	public void hourCrunch() {
		log.info("Running hourly crunching task");
	}

	@Transactional
	public void dayCrunch() {
		log.info("Running daily crunching task");
	}

}
