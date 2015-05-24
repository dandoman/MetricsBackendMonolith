package com.cante.metrics.timer;

import lombok.extern.log4j.Log4j;

@Log4j
public class MetricsCruncher implements PeriodicTask{

    public void runTask() {
       log.info("Running metrics crunching task");
    }

}
