package com.cante.metrics.activity;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cante.metrics.entity.pojo.Monitor;
import com.cante.metrics.logic.MonitorLogic;
import com.cante.metrics.request.CreateMonitorRequest;
import com.cante.metrics.response.Response;

@Log4j
@Controller
@RequestMapping(value = "/monitor")
public class MonitorActivity {
	private static final String APPLICATION_JSON = "application/json";
	@Setter private MonitorLogic logic;

    @RequestMapping(method = RequestMethod.GET, produces = { APPLICATION_JSON })
    @ResponseBody
    public Response queryMonitors() {
    	Response r = new Response();
        r.setMessage("Hello world (monitors)!");
        return r;
    }
    
    @RequestMapping(method = RequestMethod.POST, produces = { APPLICATION_JSON })
    @ResponseBody
    public Monitor createMonitor(CreateMonitorRequest r) {
    	Monitor m = new Monitor();
    	m.setApplicationName(r.getApplicationName());
    	m.setHostName(r.getHostName());
    	m.setMarketplace(r.getMarketplace());
    	m.setMetricName(r.getMetricName());
    	m.setOperation(r.getOperation());
    	m.setType(m.getType());
    	m.setCounts(r.getCounts());
    	m.setLess(r.getLess());
    	m.setThreshold(r.getThreshold());
    	
    	logic.createMonitor(m,r.getApiKey());
    	
    	return m;
    }
}
