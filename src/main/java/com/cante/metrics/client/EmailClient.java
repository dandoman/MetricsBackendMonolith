package com.cante.metrics.client;

import com.cante.metrics.entity.pojo.Metric;
import com.cante.metrics.entity.pojo.Monitor;

public interface EmailClient {
	public void sendAlarm(Monitor m, Metric currentMetric) throws Exception;
}
