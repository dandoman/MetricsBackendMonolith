package com.cante.metrics.activity;

import java.util.List;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cante.metrics.entity.StagedMetricEntity;
import com.cante.metrics.entity.pojo.StagedMetric;
import com.cante.metrics.logic.StagedMetricLogic;
import com.cante.metrics.request.BulkUploadRequest;
import com.cante.metrics.response.BulkUploadResponse;

@Log4j
@Controller
@RequestMapping(value = "/processing")
@Transactional(rollbackFor = Exception.class)
public class MetricStagingActivity {

    private static final String APPLICATION_JSON = "application/json";
    @Setter private StagedMetricLogic logic;

    
    @RequestMapping(method = RequestMethod.POST, produces = { APPLICATION_JSON }, value = "/upload")
    @ResponseBody
    public BulkUploadResponse upload(@RequestBody BulkUploadRequest request) {
    	BulkUploadResponse r = new BulkUploadResponse();
        List<StagedMetric> failures = logic.stageMetrics(request.getApiKey(),request.getMetrics());
        r.setFailedMetrics(failures);
        return r;
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = { APPLICATION_JSON })
    @ResponseBody
    public List<StagedMetricEntity> getStagedMetrics() {
        List<StagedMetricEntity> metrics = logic.getStagedMetrics();
        return metrics;
    }

}
