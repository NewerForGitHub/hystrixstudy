package com.sc.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sc.client.feign.Server1Feign;
import com.sc.client.feign.Server2Feign;
import com.sc.pojo.PojoObj;

/**
 * @author ChaoqunZheng
 */
@Service
public class RestService {
    private static final String SERVER1_URL = "http://localhost:8001/";
    private static final String RIBBON_SERVER1_URL = "http://SC-SERVER1/";
    @Autowired
    private Server1Feign server1Feign;
    @Autowired
    private Server2Feign server2Feign;

    /** ========================== feign请求 =========================== */
    public PojoObj normal() {
        return this.server1Feign.test();
    }

    public PojoObj timeout() {
        return this.server1Feign.testTimeOut();
    }

    public PojoObj bug() {
        return this.server1Feign.testBug();
    }

    /** ========================== feign请求 =========================== */

    /** ==================== hystrix-timout 请求 ======================= */
    public PojoObj threadPool() {
        return this.server1Feign.testTimeOut();
    }

    /** ==================== hystrix-timout 请求 ======================= */

    /** ==================== hystrix-fallback 请求 ======================= */
    public PojoObj timeoutFallback() {
        return this.server2Feign.testTimeOut();
    }

    public PojoObj bugFallback() {
        return this.server2Feign.testBug();
    }

    /** ==================== hystrix-fallback 请求 ======================= */

    /** ==================== hystrix-circuit 请求 ======================= */
    /**
     * 3秒钟内，请求次数达到两个，失败率在50%以上，就跳闸
     * 跳闸后，每3秒重试一次（即 3秒的活动窗口）
     *
     * @return
     */
    @HystrixCommand(threadPoolKey = "testBug", //
            commandProperties = {//HystrixCommandProperties
                @HystrixProperty(
                        name = "metrics.rollingStats.timeInMilliseconds",
                        value = "3000"),
                @HystrixProperty(
                        name = "circuitBreaker.requestVolumeThreshold",
                        value = "2"),
                @HystrixProperty(
                        name = "circuitBreaker.errorThresholdPercentage",
                        value = "50"),
                @HystrixProperty(
                        name = "circuitBreaker.sleepWindowInMilliseconds",
                        value = "3000") })
    public PojoObj testBug4Hystrix() {
        return this.server2Feign.testBug();
    }
    /** ==================== hystrix-circuit 请求 ======================= */
}
