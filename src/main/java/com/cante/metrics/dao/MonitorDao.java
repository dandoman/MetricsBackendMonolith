package com.cante.metrics.dao;

import com.cante.metrics.entity.pojo.Monitor;

public interface MonitorDao {

	public void create(Monitor m, String ownerId);

}
