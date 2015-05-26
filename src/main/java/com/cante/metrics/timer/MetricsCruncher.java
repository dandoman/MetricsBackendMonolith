package com.cante.metrics.timer;

import com.cante.metrics.dao.MetricDao;
import com.cante.metrics.dao.StagedMetricDao;
import com.cante.metrics.logic.CrunchingLogic;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class MetricsCruncher {
	@Setter StagedMetricDao stagedDao;
	@Setter MetricDao metricDao;
	@Setter CrunchingLogic logic;
	
    public void runTask() {
       log.info("Running metrics crunching task");
    }

}
