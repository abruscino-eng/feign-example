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
        response.put("exampleStatus", actuatorClient.getHealth());
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/health2")
    public Object getHealthWithQueryParams(@RequestParam(required = false) final Map<String, String> params) {
        final String param = params.entrySet().parallelStream()
                .map(it -> String.format("%s=%s", it.getKey(), it.getValue()))
                .collect(Collectors.joining("&"));
        final HashMap<String, String> response = new HashMap<>();
        response.put("exampleStatus2", exampleClient.getHealth(param));
        return response;
    }

}
