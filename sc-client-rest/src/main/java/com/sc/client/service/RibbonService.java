package com.sc.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sc.pojo.PojoObj;

/**
 * @author ChaoqunZheng
 */
@Service
public class RibbonService {
    private static final String RIBBON_SERVER1_URL = "http://SC-SERVER1/";
    /** 客户端负载均衡 */
    @Autowired
    private RestTemplate restRibbonTemplate;

    /** ======================= ribbon 请求 ========================= */
    public PojoObj normal() {
        return this.restRibbonTemplate.getForObject(
            RibbonService.RIBBON_SERVER1_URL + "scTest", PojoObj.class);
    }

    public PojoObj timeout() {
        return this.restRibbonTemplate.getForObject(
            RibbonService.RIBBON_SERVER1_URL + "scTestTimeOut", PojoObj.class);
    }

    public PojoObj bug() {
        return this.restRibbonTemplate.getForObject(
            RibbonService.RIBBON_SERVER1_URL + "scBugTest", PojoObj.class);
    }

    /** ======================= ribbon 请求 ========================= */

    /** ======================= ribbon 请求 ========================= */
    @HystrixCommand(fallbackMethod = "hystrixGet")
    public PojoObj bugFallback() {
        return this.restRibbonTemplate.getForObject(
            RibbonService.RIBBON_SERVER1_URL + "scBugTest", PojoObj.class);
    }

    /**
     * 服务熔断处理
     *
     * @return
     */
    public static PojoObj hystrixGet() {
        return new PojoObj().setName("Nobody").setAge(0);
    }

    /** ======================= ribbon 请求 ========================= */

    /** ==================== hystrix-timout 请求 ======================= */
    @HystrixCommand(
            threadPoolKey = "testThreadPool", //
            threadPoolProperties = {//HystrixThreadPoolProperties
            @HystrixProperty(name = "maxQueueSize", value = "20"),
                @HystrixProperty(name = "coreSize", value = "3") },
            commandProperties = {//HystrixCommandProperties
            @HystrixProperty(
                    name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = "2000") })
    public PojoObj testThreadPool() {
        return this.restRibbonTemplate.getForObject(
            RibbonService.RIBBON_SERVER1_URL + "scTestTimeOut", PojoObj.class);
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
        return this.restRibbonTemplate.getForObject(
            RibbonService.RIBBON_SERVER1_URL + "scBugTest", PojoObj.class);
    }
    /** ==================== hystrix-circuit 请求 ======================= */
}
