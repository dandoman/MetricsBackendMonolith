package com.cante.metrics.tests.functional;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
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
	private static final String BASE_APP_NAME = "FunctionalTest";
	private static final long FIFTEEN_MINS_IN_MILLIS = 1000 * 60 * 15;

	@Before
	public void before() {
		testClient = new MetricClient();
		testClient.init();
	}

	@Test
	public void sendSingleMetric() throws Exception {
		String metricName = BASE_METRIC_NAME + new Random().nextInt();
		StagedMetric m = createMetric(metricName, BASE_APP_NAME, 20);
		int res = testClient.createMetric(m);
		if (res < 200 || res > 299) {
			throw new Exception("Failed to upload metric with status code: "
					+ res);
		}

		System.out.println("Waiting for metric crunching");
		Thread.sleep(FIFTEEN_MINS_IN_MILLIS);
		List<Metric> metrics = testClient.getMetricByNameAndApp(metricName,
				BASE_APP_NAME);
		assertTrue(metrics.size() == 8);
		for (Metric metric : metrics) {
			assertTrue(metric.getCount() == 1);
		}

	}

	@Test
	public void sendMultipleMetrics() throws Exception {
		String app1Name = BASE_APP_NAME + new Random().nextInt();
		String app2Name = BASE_APP_NAME + new Random().nextInt();

		StagedMetric m1 = createMetric(BASE_METRIC_NAME, app1Name, 10);
		StagedMetric m2 = createMetric(BASE_METRIC_NAME, app2Name, 20);

		int res = testClient.createMetric(m1);
		if (res < 200 || res > 299) {
			throw new Exception("Failed to upload metric with status code: "
					+ res);
		}

		res = testClient.createMetric(m2);
		if (res < 200 || res > 299) {
			throw new Exception("Failed to upload metric with status code: "
					+ res);
		}

		System.out.println("Waiting for metric crunching");
		Thread.sleep(FIFTEEN_MINS_IN_MILLIS);

		List<Metric> metrics = testClient.getMetricByNameAndApp(BASE_METRIC_NAME,
				app1Name);
		for (Metric metric : metrics) {
			if(occurredWithinLast15Mins(metric)){
				assertTrue(Double.compare(metric.getAvg(), 10.0) == 0);
			}
		}

		metrics = testClient.getMetricByNameAndApp(BASE_METRIC_NAME, app2Name);
		for (Metric metric : metrics) {
			if(occurredWithinLast15Mins(metric)){
				assertTrue(Double.compare(metric.getAvg(), 20.0) == 0);
			}
		}

		metrics = testClient.getMetricByNameAndApp(BASE_METRIC_NAME,"ALL");
		for (Metric metric : metrics) {
			if(occurredWithinLast15Mins(metric)){
				assertTrue(Double.compare(metric.getAvg(), 15.0) == 0);
 			}
		}
	}
	
	private boolean occurredWithinLast15Mins(Metric m){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -15);
		Date pastTime = cal.getTime();
		
		Date metricDate = new Date(m.getTimeStamp());
		if(metricDate.after(pastTime)){
			return true;
		}
		else{
			return false;
		}
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
