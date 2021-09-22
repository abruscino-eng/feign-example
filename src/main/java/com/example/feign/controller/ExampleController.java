package com.example.feign.controller;

import com.example.feign.client.ActuatorClient;
import com.example.feign.client.ExampleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/example")
public class ExampleController {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleController.class);

    @Autowired
    ActuatorClient actuatorClient;

    @Autowired
    ExampleClient exampleClient;

    @RequestMapping(method = RequestMethod.GET, path = "/health")
    public Object getHealth(@RequestParam(required = false) final Map<String, String> params) {
        params.entrySet()
                .forEach(it -> LOG.info("ParamName: {}, ParamValue: {}", it.getKey(), it.getValue()));

        final HashMap<String, String> response = new HashMap<>();
        response.put("health", actuatorClient.getHealth());
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/healthWithReqParam")
    public Object getHealthWithReceivedQueryParams(@RequestParam(required = false) final Map<String, String> params) {
        final String param = params.entrySet().parallelStream()
                .map(it -> String.format("%s=%s", it.getKey(), it.getValue()))
                .collect(Collectors.joining("&"));
        final HashMap<String, String> response = new HashMap<>();
        response.put("healthWithReqParam", exampleClient.getHealth(param));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/healthWithoutReqParam")
    public Object getHealthWithouReceivedQueryParams() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("db", "PRD_PHO");
        params.put("db1", "PRD_GLD");
        params.put("db2", "PRD_RPP");
        final String param = params.entrySet().parallelStream()
                .map(it -> String.format("args_%1$s:%2$s=%1$s", it.getValue(), it.getKey()))
                .collect(Collectors.joining("&"));
        LOG.info("Request param: {}", param);
        final HashMap<String, String> response = new HashMap<>();
        response.put("healthWithoutReqParam", exampleClient.getHealth(param));
        return response;
    }

}
