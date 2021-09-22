package com.example.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "actuator", url = "http://localhost:8080/actuator")
public interface ActuatorClient {

    @RequestMapping(method = RequestMethod.GET, value = "/health")
    String getHealth();
}
