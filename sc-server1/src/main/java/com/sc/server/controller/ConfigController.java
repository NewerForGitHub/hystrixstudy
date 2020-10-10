package com.sc.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChaoqunZheng
 */
@RestController
public class ConfigController {
    private static final Logger logger = LoggerFactory
        .getLogger(ConfigController.class);
    @Autowired
    private DiscoveryClient client;

    @GetMapping("/services")
    public Object discovery() {
        //服务清单
        List<String> services = this.client.getServices();
        ConfigController.logger.info("services:" + services);

        // 得到具体的服务信息
        List<ServiceInstance> instances = this.client
            .getInstances("sc-server1");
        instances.forEach(e -> {
            ConfigController.logger.info(e.getHost() + "\t" + e.getPort()
                + "\t" + e.getUri() + "\t" + e.getServiceId());
        });
        return this.client;
    }
}
