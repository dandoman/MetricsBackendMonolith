package com.cante.metrics.tests.functional;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.cante.metrics.entity.pojo.StagedMetric;
import com.cante.metrics.request.CreateMonitorRequest;
import com.cante.metrics.tests.clients.EmailClient;
import com.cante.metrics.tests.clients.MetricClient;

public class AlertsFunctionalTests {
	
	private EmailClient testEmailClient;
	private MetricClient testClient;
	private static final String BASE_METRIC_NAME = "FunctionalTestMetric";
	private static final String BASE_APP_NAME = "FunctionalTest";
	private static final long FIFTEEN_MINS_IN_MILLIS = 1000 * 60 * 21;
	
	@Before
	public void before(){
		testClient = new MetricClient();
		testClient.init();
		testEmailClient = new EmailClient();
	}
	
	@Test
	public void testAlert() throws Exception{
		String metricName = BASE_METRIC_NAME + new Random().nextInt();
		StagedMetric m1 = createMetric(metricName, BASE_APP_NAME, 20);
		StagedMetric m2 = createMetric(metricName, BASE_APP_NAME, 10);
		StagedMetric m3 = createMetric(metricName, BASE_APP_NAME, 18);
		
		CreateMonitorRequest r = new CreateMonitorRequest();
		r.setApiKey("disAPI");
		r.setApplicationName(m1.getApplicationName());
		r.setCounts(1);
		r.setDescription("OH MAH GAWD, AN ALARM");
		r.setEmailRecipient("cante.metrics@gmail.com");
		r.setHostName("ALL");
		r.setLess(false);
		r.setMarketplace("ALL");
		r.setMetricName(m1.getMetricName());
		r.setOperation("ALL");
		r.setThreshold(11.0);
		r.setType("p50");
		
		testClient.createMonitor(r);
		
		
		int res = testClient.createMetric(m1);
		if (res < 200 || res > 299) {
			throw new RuntimeException("Failed to upload metric with status code: "
					+ res);
		}
		
		res = testClient.createMetric(m2);
		if (res < 200 || res > 299) {
			throw new RuntimeException("Failed to upload metric with status code: "
					+ res);
		}
		
		res = testClient.createMetric(m3);
		if (res < 200 || res > 299) {
			throw new RuntimeException("Failed to upload metric with status code: "
					+ res);
		}
		
		System.out.println("Waiting for metric crunching and alert processing");
		Thread.sleep(FIFTEEN_MINS_IN_MILLIS);
		
		assertTrue(testEmailClient.areThereNewMessages());
		
		
	}
	
	private StagedMetric createMetric(String name, String appName, double val) {
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
