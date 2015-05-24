package com.cante.metrics.activity;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j
@Controller
public class MetricStagingActivity {

    private static final String APPLICATION_JSON = "application/json";

    @RequestMapping(method = RequestMethod.GET, produces = { APPLICATION_JSON }, value = "/write")
    @ResponseBody
    public String helloWorld() {
        String message = "Hello World!";
        log.warn(message);
        return message;
    }

}
