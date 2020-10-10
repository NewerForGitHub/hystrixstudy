package com.sc.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.sc.pojo.PojoObj;

/**
 * @author ChaoqunZheng
 */
@Component
@FeignClient("sc-server1")
public interface Server1Feign {
    @GetMapping("/scTest")
    PojoObj test();

    @GetMapping("/scTestTimeOut")
    PojoObj testTimeOut();

    @GetMapping("/scBugTest")
    PojoObj testBug();
}
