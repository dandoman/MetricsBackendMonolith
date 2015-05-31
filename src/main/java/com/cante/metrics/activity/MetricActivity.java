package com.cante.metrics.activity;

import java.util.List;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.entity.pojo.SearchParameters;
import com.cante.metrics.logic.MetricLogic;
import com.cante.metrics.response.Response;

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
			@RequestParam(required = false) Long endTime) {
		SearchParameters sp = new SearchParameters();
		sp.setApplicationName(applicationName);
		sp.setHostName(hostName);
		sp.setOperation(operation);
		sp.setMarketplace(marketplace);
		sp.setMetricName(metricName);
		sp.setStartTime(startTime);
		sp.setEndTime(endTime);
		return metricLogic.search(sp);
	}
}
