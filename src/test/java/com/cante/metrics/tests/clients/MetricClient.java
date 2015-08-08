package com.cante.metrics.tests.clients;

import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.entity.pojo.StagedMetric;
import com.cante.metrics.request.BulkUploadRequest;
import com.cante.metrics.request.CreateMonitorRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Setter;

public class MetricClient {

	@Setter
	private HttpClient client;
	private ObjectMapper mapper = new ObjectMapper();

	public void init() {
		HttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		client = HttpClientBuilder.create()
				.setConnectionManager(connectionManager).build();
	}

	public int createMetric(StagedMetric metric) {
		HttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		client = HttpClientBuilder.create()
				.setConnectionManager(connectionManager).build();
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http")
				.setHost("ec2-52-24-132-32.us-west-2.compute.amazonaws.com")
				.setPort(8080).setPath("/MetricsService/processing/upload");
		BulkUploadRequest req = new BulkUploadRequest();
		List<StagedMetric> metrics = new ArrayList<StagedMetric>();
		metrics.add(metric);
		req.setApiKey("mahKeyy");
		req.setMetrics(metrics);

		try {
			HttpPost post = new HttpPost(builder.build());
			post.setHeader("Content-Type", "application/json");
			post.setEntity(EntityBuilder.create()
					.setBinary(mapper.writeValueAsBytes(req)).build());
			HttpResponse r = client.execute(post);
			System.out.println("Created metric: " + metric.toString());
			return r.getStatusLine().getStatusCode();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Metric> getMetricByNameAndApp(String name, String appName) {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http")
				.setHost("ec2-52-24-132-32.us-west-2.compute.amazonaws.com")
				.setPort(8080).setPath("/MetricsService/metric/search")
				.setParameter("metricName", name)
				.setParameter("applicationName", appName);

		try {
			HttpGet get = new HttpGet(builder.build());
			HttpResponse response = client.execute(get);
			List<Metric> returnMetrics = mapper.readValue(response.getEntity()
					.getContent(), mapper.getTypeFactory()
					.constructCollectionType(ArrayList.class, Metric.class));
			return returnMetrics;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static void main(String[] args) throws Exception {
		MetricClient client = new MetricClient();
		client.init();
		StagedMetric newMetric = new StagedMetric();
		newMetric.setApplicationName("FunctionalTest");
		newMetric.setEndTime(new Date().getTime());
		newMetric.setStartTime(new Date().getTime());
		newMetric.setHostName(InetAddress.getLocalHost().getHostName());
		newMetric.setMarketplace("pokemon");
		newMetric.setOperation("testyMcTest");
		newMetric.setValue(100);
		System.out.println("Status code for create: "
				+ client.createMetric(newMetric));
		System.out.println(client.getMetricByNameAndApp("mahMetric",
				"FunctionalTest"));
	}

	public void createMonitor(CreateMonitorRequest req) throws Exception {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http")
				.setHost("ec2-52-24-132-32.us-west-2.compute.amazonaws.com")
				.setPort(8080).setPath("/MetricsService/monitor");

		HttpPost post = new HttpPost(builder.build());
		post.setHeader("Content-Type", "application/json");
		post.setEntity(EntityBuilder.create()
				.setBinary(mapper.writeValueAsBytes(req)).build());
		HttpResponse r = client.execute(post);
		if(r.getStatusLine().getStatusCode() != 200){
			throw new Exception("Did not create monitor");
		}
	}
}
