package com.groupon.specials.timer;

import lombok.extern.log4j.Log4j;

@Log4j
public class SpecialsClaimPublisher implements PeriodicTask{

    public void runTask() {
       log.info("Running specials claim publish task");
    }

}
