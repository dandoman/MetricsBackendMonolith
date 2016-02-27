package com.cante.metrics.scripts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import lombok.Cleanup;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.cante.metrics.entity.pojo.StagedMetric;
import com.cante.metrics.request.BulkUploadRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MetricCreator {

	private static final String API_KEY = "5a41733e-df01-404f-a36e-1053d73f2fd7";
	private static HttpClient client;
	private static final String APP_NAME = "TestApp";
	private static final String OPERATION = "TestOperation";
	private static final String MARKETPLACE = "TestMarketplace";
	private static final String HOSTNAME = "8.8.8.8";
	private static final ObjectMapper mapper = new ObjectMapper();

	static {
		HttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		client = HttpClientBuilder.create()
				.setConnectionManager(connectionManager).build();
	}

	public static void main(String[] args) throws Exception {
		Random random = new Random();
		@Cleanup
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the name of your metric");
		String input = in.nextLine();
		input = input.trim();
		input = input.replaceAll("\\s+", "-");
		
		System.out.println("How many data points do you wish to create? (Max 1000)");
		String iterations = in.nextLine();
		int noIterations = 1;
		try{
			noIterations = Integer.parseInt(iterations);
		} catch (Exception e){
			//Do nothing
		}
		
		if(noIterations > 1000){
			noIterations = 1000;
		}

		List<StagedMetric> metrics = new ArrayList<StagedMetric>();
		
		for(int i = 0; i < noIterations; i++){
			StagedMetric metric = buildMetric(input, Math.abs(random.nextInt(5000)));
			metrics.add(metric);
		}
		
		sendMetrics(metrics);
	}
	
	private static StagedMetric buildMetric(String name, int value){
		StagedMetric metric = new StagedMetric();
		metric.setApplicationName(APP_NAME);
		metric.setTimeStamp(new Date().getTime());
		metric.setHostName(HOSTNAME);
		metric.setMarketplace(MARKETPLACE);
		metric.setOperation(OPERATION);
		metric.setValue(value);
		metric.setMetricName(name);
		
		return metric;
	}
	private static void sendMetrics(List<StagedMetric> metrics) throws Exception{
		BulkUploadRequest request = new BulkUploadRequest();
		request.setApiKey(API_KEY);
		request.setMetrics(metrics);

		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setPort(8080)
				.setHost("ec2-52-88-83-153.us-west-2.compute.amazonaws.com")
				.setPath("/MetricsService/processing/upload");
		
		HttpPost post = new HttpPost(builder.build());
		post.setEntity(new ByteArrayEntity(mapper.writeValueAsBytes(request)));
		post.setHeader("Content-Type", "application/json");
		
		HttpResponse response = client.execute(post);
		String result = EntityUtils.toString(response.getEntity());

		if (response.getStatusLine().getStatusCode() > 299) {
			System.out.println("Call failed with following response: \n"
					+ result + "\n and code: " + response.getStatusLine().getStatusCode());
		}
	}
}
