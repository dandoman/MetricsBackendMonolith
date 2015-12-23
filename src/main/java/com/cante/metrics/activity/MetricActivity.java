package com.cante.metrics.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.entity.pojo.MetricHeader;
import com.cante.metrics.entity.pojo.SearchParameters;
import com.cante.metrics.logic.MetricLogic;
import com.cante.metrics.response.SearchParamResponse;

@Log4j
@Controller
@RequestMapping(value = "/metric")
@Transactional
public class MetricActivity {
	@Setter
	private MetricLogic metricLogic;
	private static final String APPLICATION_JSON = "application/json";

	@RequestMapping(method = RequestMethod.GET, produces = { APPLICATION_JSON })
	@ResponseBody
	public List<Metric> getMetrics() {
		return metricLogic.getMetrics();
	}

	@RequestMapping(method = RequestMethod.GET, produces = { APPLICATION_JSON }, value = "/search")
	@ResponseBody
	public List<Metric> searchMetrics(
			@RequestParam(required = false) String applicationName,
			@RequestParam(required = false) String hostName,
			@RequestParam(required = false) String operation,
			@RequestParam(required = false) String marketplace,
			@RequestParam(required = false) String metricName,
			@RequestParam(required = false) Long startTime,
			@RequestParam(required = false) Long endTime,
			@RequestParam(required = true) String customerId, HttpServletResponse response) {
		
		
		response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
		
		SearchParameters sp = new SearchParameters();
		sp.setApplicationName(applicationName);
		sp.setHostName(hostName);
		sp.setOperation(operation);
		sp.setMarketplace(marketplace);
		sp.setMetricName(metricName);
		sp.setStartTime(startTime);
		sp.setEndTime(endTime);
		return metricLogic.search(sp, customerId);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { APPLICATION_JSON }, value = "/searchParams")
	@ResponseBody
	public SearchParamResponse searchForParams(
			@RequestParam(required = true) String param,
			@RequestParam(required = false) String applicationName,
			@RequestParam(required = false) String hostName,
			@RequestParam(required = false) String operation,
			@RequestParam(required = false) String marketplace,
			@RequestParam(required = false) String metricName,
			@RequestParam(required = true) String customerId,
			@RequestParam(required = false) Long startTime,
			@RequestParam(required = false) Long endTime, HttpServletResponse response) {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");

		if (startTime == null) {
			startTime = DateTime.now().minusDays(3).getMillis();
		}

		if (endTime == null) {
			endTime = DateTime.now().getMillis();
		}

		List<MetricHeader> searchparams = metricLogic.getSearchparamsFor(param,
				customerId, startTime, endTime);
		Set<String> applicationNames = new HashSet<String>();
		Set<String> operationNames = new HashSet<String>();
		Set<String> marketPlaces = new HashSet<String>();
		Set<String> hostnames = new HashSet<String>();
		Set<String> metricNames = new HashSet<String>();
		//TODO Hack fuck shit, want to do actual filtering on the db, fix
		List<MetricHeader> filteredParams = new ArrayList<MetricHeader>();
		
		for(MetricHeader header : searchparams) {
			if(!StringUtils.isEmpty(applicationName)){
				if(!header.getApplicationName().equals(applicationName)){
					continue;
				}
			}
			
			if(!StringUtils.isEmpty(hostName)){
				if(!header.getHostname().equals(hostName)){
					continue;
				}
			}
			
			if(!StringUtils.isEmpty(operation)){
				if(!header.getOperation().equals(operation)){
					continue;
				}
			}
			
			if(!StringUtils.isEmpty(marketplace)){
				if(!header.getMarketplace().equals(marketplace)){
					continue;
				}
			}
			
			if(!StringUtils.isEmpty(metricName)){
				if(!header.getMetricName().equals(metricName)){
					continue;
				}
			}
			
			filteredParams.add(header);
		}
		
		
		
		for (MetricHeader header : filteredParams) {
			applicationNames.add(header.getApplicationName());
			operationNames.add(header.getOperation());
			marketPlaces.add(header.getMarketplace());
			hostnames.add(header.getHostname());
			metricNames.add(header.getMetricName());
		}
		return new SearchParamResponse(searchparams, new ArrayList<String>(
				applicationNames), new ArrayList<String>(hostnames),
				new ArrayList<String>(operationNames), new ArrayList<String>(marketPlaces), new ArrayList<String>(metricNames));
	}
}
