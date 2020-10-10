package com.sc.client.feign.impl;

import org.springframework.stereotype.Component;

import com.sc.client.feign.Server2Feign;
import com.sc.pojo.PojoObj;

/**
 * @author ChaoqunZheng
 */
@Component
public class Server2FeignImpl implements Server2Feign {
    @Override
    public PojoObj test() {
        return null;
    }

    @Override
    public PojoObj testTimeOut() {
        return null;
    }

    @Override
    public PojoObj testBug() {
        return new PojoObj().setName("Hystrix").setAge(0);
    }
}
