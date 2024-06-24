package com.example.loanprovisioning.metric;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("app/customeMetric")
@Slf4j
public class CustomeMetricController {

    private final PublicMetricConfig publicMetricConfig;

    public CustomeMetricController(PublicMetricConfig publicMetricConfig) {
        this.publicMetricConfig = publicMetricConfig;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Metric> getCustomeMetric() {
        return publicMetricConfig.metrics();
    }
}
