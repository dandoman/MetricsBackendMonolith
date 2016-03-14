package com.cante.metrics.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.cante.metrics.client.EmailClient;
import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.entity.pojo.Monitor;
import com.cante.metrics.entity.pojo.MonitorSearchParam;
import com.cante.metrics.entity.pojo.SearchParameters;
import com.cante.metrics.logic.LockManagementLogic;
import com.cante.metrics.logic.MetricLogic;
import com.cante.metrics.logic.MonitorLogic;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class MonitorProcessor {
	@Setter
	private LockManagementLogic lockLogic;
	@Setter private String selfHostId;
	@Setter private MonitorLogic monitorLogic;
	@Setter private EmailClient emailClient;
	@Setter private MetricLogic metricLogic;
	
	public void init() {
		selfHostId = selfHostId + "-" + UUID.randomUUID().toString();
		log.info(String.format("Monitor processor using %s as host id", selfHostId));
	}
	
	@Transactional
	public void runTask() {
		log.info("Running monitor processing task");
		List<String> ownerIds = lockLogic
				.getOwnerIdsForResponsibility(selfHostId);
		
		for(String id : ownerIds){
			MonitorSearchParam p = new MonitorSearchParam();
			p.setOwnerId(id);
			List<Monitor> monitors = monitorLogic.queryMonitors(p);
			processMonitors(monitors,id);
		}
		
	}

	private void processMonitors(List<Monitor> monitors, String ownerId) {
		for(Monitor m : monitors){
			log.info("Processing monitor: " + m + " for owner: " + ownerId);
			checkMonitor(m,ownerId);
		}
	}

	private void checkMonitor(Monitor m, String ownerId) {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MINUTE, -17);
		Date end = cal.getTime();
		cal.setTime(now);
		cal.add(Calendar.MINUTE, -22);
		Date start = cal.getTime();
		
		SearchParameters p = new SearchParameters();
		p.setApplicationName(m.getApplicationName());
		p.setHostName(m.getHostName());
		p.setMarketplace(m.getMarketplace());
		p.setOperation(m.getOperation());
		p.setMetricName(m.getMetricName());
		p.setStartTime(start.getTime());
		p.setEndTime(end.getTime());
		List<Metric> metrics = metricLogic.search(p,ownerId);
		if(metrics.size() > 1){
			log.warn("Metrics list size > 1, potential problems");
		}

		//Check if the current monitor is past threshold to alarm
			//Check if we have enough counts (including this one) to fire email
				//If we fire, reset count
		
		//Check if current monitor is not past threshold to alarm
			//Reset the counts
			
		//If metric is not present
			//Check if monitor is a '<' monitor
				//If so, must treat as fired, proceed normally
		
		if(metrics.size() == 0){
			if(m.getLess()){
				fireMonitor(m, null);
			}
			return;
		}
		
		Metric metric = metrics.get(0);
		if(needToFire(metric,m)){
			fireMonitor(m, metric);
			return;
		}
		else{
			monitorLogic.resetMonitor(m);
			return;
		}
		
	}

	//Refactor this dirtyness (tano)
	private boolean needToFire(Metric metric, Monitor m) {
		if(m.getLess()){
			switch(m.getType()){
			case "p0":
				return Double.compare(metric.getP0(), m.getThreshold()) < 0;
			case "p50":
				return Double.compare(metric.getP50(), m.getThreshold()) < 0;
			case "p75":
				return Double.compare(metric.getP75(), m.getThreshold()) < 0;
			case "p90":
				return Double.compare(metric.getP90(), m.getThreshold()) < 0;
			case "p99":
				return Double.compare(metric.getP99(), m.getThreshold()) < 0;
			case "p999":
				return Double.compare(metric.getP999(), m.getThreshold()) < 0;
			case "p9999":
				return Double.compare(metric.getP9999(), m.getThreshold()) < 0;
			case "p100":
				return Double.compare(metric.getP100(), m.getThreshold()) < 0;
			case "avg":
				return Double.compare(metric.getAvg(), m.getThreshold()) < 0;
			case "count":
				return Double.compare(metric.getCount(), m.getThreshold()) < 0;
			case "sum":
				return Double.compare(metric.getCount(), m.getThreshold()) < 0;
			}
		}
		else{
			switch(m.getType()){
			case "p0":
				return Double.compare(metric.getP0(), m.getThreshold()) > 0;
			case "p50":
				return Double.compare(metric.getP50(), m.getThreshold()) > 0;
			case "p75":
				return Double.compare(metric.getP75(), m.getThreshold()) > 0;
			case "p90":
				return Double.compare(metric.getP90(), m.getThreshold()) > 0;
			case "p99":
				return Double.compare(metric.getP99(), m.getThreshold()) > 0;
			case "p999":
				return Double.compare(metric.getP999(), m.getThreshold()) > 0;
			case "p9999":
				return Double.compare(metric.getP9999(), m.getThreshold()) > 0;
			case "p100":
				return Double.compare(metric.getP100(), m.getThreshold()) > 0;
			case "avg":
				return Double.compare(metric.getAvg(), m.getThreshold()) > 0;
			case "count":
				return Double.compare(metric.getCount(), m.getThreshold()) > 0;
			case "sum":
				return Double.compare(metric.getCount(), m.getThreshold()) > 0;
			}
		}
		
		return false;
		
	}

	private void fireMonitor(Monitor m, Metric currentMetric) {
		monitorLogic.fireMonitor(m);
		
		//For now, we fire every time. In future, we implement a number of counts before we fire
		int countBeforeFire = 1;
		int countIncludingCurrent = m.getCounts() + 1;
		
		if(countIncludingCurrent > countBeforeFire){
			try {
				log.info("Sending email to " + m.getEmailRecipient());
				emailClient.sendAlarm(m, currentMetric);
			} catch (Exception e) {
				log.error(String.format("Error when sending email for %s",m.toString()),e);
			}
			
			monitorLogic.resetMonitor(m);
		}
	}

}
