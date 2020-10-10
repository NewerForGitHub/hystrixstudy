package com.sc.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sc.client.service.RibbonService;
import com.sc.pojo.PojoObj;

/**
 * @author zhengchaoqun
 */
@RestController
@RequestMapping("/ribbon")
public class RibbonController {
    @Autowired
    private RibbonService ribbonService;

    /**
     * ribbin-normal 请求
     * 
     * @return
     */
    @GetMapping(value = "/normal", produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj normal() {
        return this.ribbonService.normal();
    }

    /**
     * ribbon-timeout 请求
     *
     * @return
     */
    @GetMapping(value = "/timeout", produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj timeout() {
        return this.ribbonService.timeout();
    }

    /**
     * ribbon-bug 请求
     * 
     * @return
     */
    @GetMapping(value = "/bug", produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj bug() {
        return this.ribbonService.bug();
    }

    /**
     * ribbon-bug-fallback 请求
     *
     * @return
     */
    @GetMapping(value = "/bugFallback",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PojoObj bugFallback() {
        return this.ribbonService.bugFallback();
    }

}
