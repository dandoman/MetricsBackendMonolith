package com.cante.metrics.logic;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import com.cante.metrics.dao.CustomerDao;

/*
 * As hosts come online, they will try to assign themselves responsibility for processing the monitors for different customers
 * They may take away responsibilities from other hosts
 * Each 'lock' will have a created timestamp and a lastRun timestamp. Other hosts will be responsible for taking over a defunct lock
 * Defunct locks will be detected when each host runs their respective crunch task
 * This will have to be done differently when the data is sharded
 * Locks will need rvns as they need to provide for optimistic locking
 */
public class LockManagementLogic {
	
	@Setter CustomerDao dao;
	
	public List<String> getOwnerIdsForResponsibility(String appId){
		return dao.getAllCustomerIds();
	}

}
