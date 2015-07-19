package com.cante.metrics.tests.functional;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.entity.pojo.StagedMetric;
import com.cante.metrics.tests.clients.MetricClient;

public class MetricsFunctionalTests {
	
	private MetricClient testClient;
	private static final String BASE_METRIC_NAME = "FunctionalTestMetric";
	private static final String APP_NAME = "FunctionalTest";
	private static final long ELEVEN_MINS_IN_MILLIS = 1000 * 60 * 15;
	
	@Before
	public void before(){
		testClient = new MetricClient();
		testClient.init();
	}
	
	@Test
	public void sendSingleMetric() throws Exception{
		String metricName = BASE_METRIC_NAME + new Random().nextInt();
		StagedMetric m = createMetric(metricName, APP_NAME,20);
		int res = testClient.createMetric(m);
		if(res < 200 || res > 299){
			throw new Exception("Failed to upload metric with status code: " + res);
		}
		
		System.out.println("Waiting for metric crunching");
		Thread.sleep(ELEVEN_MINS_IN_MILLIS);
		List<Metric> metrics = testClient.getMetricByNameAndApp(metricName, APP_NAME);
		assertTrue(metrics.size() == 8);
		for(Metric metric : metrics){
			assertTrue(metric.getCount() == 1);
		}
		
	}
	
	@Test
	public void sendMultipleMetrics(){
		
	}
	
	private StagedMetric createMetric(String name,String appName, double val){
		StagedMetric newMetric = new StagedMetric();
		newMetric.setApplicationName(appName);
		newMetric.setMetricName(name);
		newMetric.setEndTime(new Date().getTime());
		newMetric.setStartTime(new Date().getTime());
		newMetric.setMarketplace("pokemon");
		newMetric.setOperation("testyMcTest");
		newMetric.setValue(val);
		newMetric.setHostName("testHost");
		return newMetric;
	}
}
