package com.cante.metrics.dao;

import java.util.List;

import com.cante.metrics.entity.pojo.Monitor;
import com.cante.metrics.entity.pojo.MonitorSearchParam;

public interface MonitorDao {

	public Monitor create(Monitor m, String ownerId);
	public List<Monitor> queryMonitors(MonitorSearchParam p);

}
