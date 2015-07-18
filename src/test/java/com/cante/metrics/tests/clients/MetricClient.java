package com.cante.metrics.tests.clients;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.entity.pojo.StagedMetric;
import com.cante.metrics.request.BulkUploadRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Setter;

public class MetricClient {
	private static final String BASE_URL = "";

	@Setter
	private HttpClient client;
	private ObjectMapper mapper = new ObjectMapper();

	public void init() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		client = HttpClientBuilder.create()
				.setConnectionManager(connectionManager).build();
	}

	public int createMetric(StagedMetric metric) {
		HttpPost post = new HttpPost(BASE_URL + "");
		BulkUploadRequest req = new BulkUploadRequest();
		List<StagedMetric> metrics = new ArrayList<StagedMetric>();
		metrics.add(metric);
		req.setApiKey("mahKeyy");
		req.setMetrics(metrics);
		
		try {
			post.setEntity(EntityBuilder.create()
					.setBinary(mapper.writeValueAsBytes(req)).build());
			HttpResponse r = client.execute(post);
			return r.getStatusLine().getStatusCode();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public List<Metric> getMetricByNameAndApp(String name,String appName){
		URIBuilder builder = new URIBuilder();
	    builder.setScheme("http").setHost("ec2-52-24-132-32.us-west-2.compute.amazonaws.com").setPort(8080).setPath("/MetricsService/metric/search")
	    .setParameter("metricName", name).setParameter("applicationName", appName);
	    
		try {
			HttpGet get = new HttpGet(builder.build());
			System.out.println("Query: " + get.getURI().getQuery());
			HttpResponse response = client.execute(get);
			List<Metric> returnMetrics = mapper.readValue(
					response.getEntity().getContent(),
					mapper.getTypeFactory().constructCollectionType(
							ArrayList.class, Metric.class));
			return returnMetrics;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static void main(String [] args){
		MetricClient client = new MetricClient();
		client.init();
		System.out.println(client.getMetricByNameAndApp("metric2", "TestApp"));
	}
}
