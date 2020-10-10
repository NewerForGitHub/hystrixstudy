package com.sc.server.controller;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sc.pojo.PojoObj;

@RestController
public class ServerController {
    private static final Logger logger = LoggerFactory
        .getLogger(ServerController.class);

    @GetMapping("/scTest")
    public PojoObj test() {
        ServerController.logger.info("server2-request");
        return new PojoObj().setAge(11).setName("Maple");
    }

    @GetMapping("/scTestTimeOut")
    public PojoObj testTimeOut() {
        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ServerController.logger.info("server2-request");
        return new PojoObj().setAge(11).setName("Maple");
    }

    @GetMapping("/scBugTest")
    public PojoObj scBugTest() {
        ServerController.logger.info("server2-request-hystrix");
        throw new RuntimeException("Hystrix Error");
    }

}
