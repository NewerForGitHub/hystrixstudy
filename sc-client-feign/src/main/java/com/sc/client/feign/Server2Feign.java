package com.sc.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.sc.pojo.PojoObj;

/**
 * 必须设置feign.hystrix.enabled=true
 * 
 * @author ChaoqunZheng
 */
@Component
@FeignClient(value = "sc-server2", //fallback = Server2FeignImpl.class,
        fallbackFactory = Server2FeignFactory.class)
public interface Server2Feign {
    @GetMapping("/scTest")
    PojoObj test();

    @GetMapping("/scTestTimeOut")
    PojoObj testTimeOut();

    @GetMapping("/scBugTest")
    PojoObj testBug();
}
