package com.sc.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sc.client.service.RestService;
import com.sc.pojo.PojoObj;

/**
 * @author zhengchaoqun
 */
@RestController
public class ClientController {
    @Autowired
    private RestService restService;

    /**
     * feign-normal 请求
     *
     * @return
     */
    @GetMapping(value = "/normal", produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj normal() {
        return this.restService.normal();
    }

    /**
     * feign-timeout 请求
     *
     * @return
     */
    @GetMapping(value = "/timeout", produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj timeout() {
        return this.restService.timeout();
    }

    /**
     * 带微服务熔断备用方案的feign请求
     *
     * @return
     */
    @GetMapping(value = "/timeoutFallback",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj timeoutFallback() {
        return this.restService.timeoutFallback();
    }

    /**
     * feign-bug 请求
     *
     * @return
     */
    @GetMapping(value = "/bug", produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj bug() {
        return this.restService.bug();
    }

    /**
     * 带微服务熔断备用方案的feign请求
     *
     * @return
     */
    @GetMapping(value = "/bugFallback",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj bugFallback() {
        return this.restService.bugFallback();
    }

    /**
     * 服务端超时，请求
     *
     * @return
     */
    @GetMapping(value = "/testThreadPool",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj testThreadPool() {
        return this.restService.threadPool();
    }

    /**
     * 服务端Bug,带熔断，请求
     *
     * @return
     */
    @GetMapping(value = "/testBug4Histrix",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj testBug4Histrix() {
        return this.restService.testBug4Hystrix();
    }
}
