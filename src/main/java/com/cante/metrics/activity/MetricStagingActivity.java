package com.cante.metrics.activity;

import javax.transaction.Transactional;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cante.metrics.logic.StagedMetricLogic;
import com.cante.metrics.request.BulkUploadRequest;
import com.cante.metrics.response.Response;

@Log4j
@Controller
@RequestMapping(value = "/processing")
public class MetricStagingActivity {

    private static final String APPLICATION_JSON = "application/json";
    @Setter private StagedMetricLogic logic;

    @Transactional
    @RequestMapping(method = RequestMethod.POST, produces = { APPLICATION_JSON }, value = "/upload")
    @ResponseBody
    public Response upload(BulkUploadRequest request) {
        Response r = new Response();
        logic.stageMetrics(request.getApiKey(),request.getMetrics());
        return r;
    }

}
