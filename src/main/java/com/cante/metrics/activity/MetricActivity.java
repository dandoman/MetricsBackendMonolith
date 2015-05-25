package com.cante.metrics.activity;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cante.metrics.logic.MetricLogic;
import com.cante.metrics.response.Response;

@Log4j
@Controller
@RequestMapping(value = "/metric")
public class MetricActivity {
    
    private static final String APPLICATION_JSON = "application/json";
    
    @RequestMapping(method = RequestMethod.GET, produces = { APPLICATION_JSON })
    @ResponseBody
    public Response getMetrics() {
       Response r = new Response();
       r.setMessage("Hello world (metrics)!");
       return r;
    }
}
