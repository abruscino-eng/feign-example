package com.example.feign.client;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "example", url = "http://localhost:8080/example")
public interface ExampleClient {

    @RequestMapping(method = RequestMethod.GET, value = "/health")
    @RequestLine(value = "param", decodeSlash = false)
    String getHealth(@RequestParam(name = "param") final String param);
}
