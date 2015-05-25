package com.cante.metrics.activity;

import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cante.metrics.response.Response;

@Log4j
@Controller
@RequestMapping(value = "/monitor")
public class MonitorActivity {
	private static final String APPLICATION_JSON = "application/json";

    @RequestMapping(method = RequestMethod.GET, produces = { APPLICATION_JSON })
    @ResponseBody
    public Response getMonitor() {
    	Response r = new Response();
        r.setMessage("Hello world (monitors)!");
        return r;
    }
}
