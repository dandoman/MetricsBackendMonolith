package com.cante.metrics.dao;

import java.util.List;

import com.cante.metrics.entity.pojo.Monitor;
import com.cante.metrics.entity.pojo.MonitorSearchParam;

public interface MonitorDao {

	public Monitor create(Monitor m, String ownerId);
	public List<Monitor> queryMonitors(MonitorSearchParam p);
	public void fireMonitor(Monitor m);
	public void resetMonitor(Monitor m);
	public void deleteMonitor(String id);

}
