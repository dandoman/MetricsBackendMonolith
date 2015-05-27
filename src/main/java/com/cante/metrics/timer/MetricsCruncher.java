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
import com.cante.metrics.entity.MetricEntity;
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
		log.info(String.format("Cruncher using %s as host id",selfHostId));
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
			
			//All,All,All,All,metric,value
			List<StagedMetricEntity> metrics = crunchingLogic.aggregate(stagedMetrics,false,false,false,false); //100%
			MetricEntity metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//App,All,All,All,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,true,false,false,false); //75%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//All,operation,All,All,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,false,true,false,false); //75%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//All,All,marketplace,All,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,false,false,true,false); //75%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//All,All,All,hostname,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,false,false,false,true); //75%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//App,operation,All,All,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,true,true,false,false); //50%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//App,All,marketplace,All,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,true,false,true,false); //50%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//App,All,All,hostname,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,true,false,false,true); //50%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//All,operation,marketplace,All,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,false,true,true,false); //50%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//All,operation,All,hostname,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,false,true,false,true); //50%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//All,All,marketplace,hostname,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,false,false,true,true); //50%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//App,operation,marketplace,All,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,true,true,true,false); //25%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//App,operation,All,hostname,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,true,true,false,true); //25%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//App,All,marketplace,hostname,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,true,false,true,true); //25%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//All,operation,marketplace,hostname,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,false,true,true,true); //25%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
			//App,operation,marketplace,hostname,metric,value
			metrics = crunchingLogic.aggregate(stagedMetrics,true,true,true,true); //5%
			metricRow = crunchingLogic.computeMetricRow(metrics,null);
			metricDao.create(id, metricRow);
			
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
