package com.cante.metrics.timer;

import org.springframework.transaction.annotation.Transactional;

import lombok.Setter;

import com.cante.metrics.logic.LockManagementLogic;

public class LockManager {
	@Setter private LockManagementLogic lockLogic;
	
	@Transactional
	public void getLocks(){
		
	}
}
