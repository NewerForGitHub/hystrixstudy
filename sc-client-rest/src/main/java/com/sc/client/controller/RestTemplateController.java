package com.sc.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sc.client.service.RestService;
import com.sc.pojo.PojoObj;

/**
 * @author zhengchaoqun
 */
@RestController
@RequestMapping("/rest")
public class RestTemplateController {
    @Autowired
    private RestService restService;

    /**
     * rest-normal 请求
     * 
     * @return
     */
    @GetMapping(value = "/normal", produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj normal() {
        return this.restService.normal();
    }

    /**
     * rest-timeout 请求
     *
     * @return
     */
    @GetMapping(value = "/timeout", produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj timeout() {
        return this.restService.timeout();
    }

    /**
     * rest-bug 请求
     *
     * @return
     */
    @GetMapping(value = "/bug", produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj bug() {
        return this.restService.bug();
    }

    /**
     * rest-bug-fallback 请求
     *
     * @return
     */
    @GetMapping(value = "/bugFallback",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj bugFallback() {
        return this.restService.bugFallback();
    }

    /**
     * rest-bug-hystrix 请求
     *
     * @return
     */
    @GetMapping(value = "/bugHistrix",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj bugHistrix() {
        return this.restService.testBug4Hystrix();
    }

    /**
     * rest-timout-breaker 请求
     *
     * @return
     */
    @GetMapping(value = "/timeoutBreaker",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj timeoutBreaker() {
        return this.restService.timeoutBreaker();
    }

}
