package com.cante.metrics.dao;

import java.util.Date;
import java.util.List;

import com.cante.metrics.entity.Lock;

public interface LockDao {
	public List<Lock> getLocksForHost(String hostId);
	public List<Lock> getLocksLastUpdatedBeforeTimestamp(Date timestamp);
}
