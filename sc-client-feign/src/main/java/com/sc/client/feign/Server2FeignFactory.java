package com.sc.client.feign;

import org.springframework.stereotype.Component;

import com.sc.pojo.PojoObj;

import feign.hystrix.FallbackFactory;

/**
 * @author ChaoqunZheng
 */
@Component
public class Server2FeignFactory implements FallbackFactory<Server2Feign> {
    @Override
    public Server2Feign create(Throwable throwable) {
        return new Server2Feign() {
            @Override
            public PojoObj test() {
                return null;
            }

            @Override
            public PojoObj testTimeOut() {
                return new PojoObj().setName("CircuitBreaker").setAge(0);
            }

            @Override
            public PojoObj testBug() {
                return new PojoObj()
                    .setName("Hystrix" + throwable.getMessage()).setAge(0);
            }
        };
    }
}
