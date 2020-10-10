hystrixstudy
------
[官方文档](https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/1.4.7.RELEASE/single/spring-cloud-netflix.html)

<h3>WHAT？什么是熔断、降级</h3>
&nbsp;&nbsp;熔断：目标服务响应慢或者有大量超时发生，中断该服务的调用，对于后续调用请求，不在继续调用目标服务，直接返回，快速释放资源。如果目标服务情况好转则恢复调用。

&nbsp;&nbsp;降级：当服务器压力剧增的情况下，根据当前业务情况及流量对一些服务和页面有策略的降级，以此释放服务器资源以保证核心任务的正常运行。

-----
<h3>WHY？为什么需要服务熔断</h3>
<h4>避免级联故障导致雪崩</h4>

&nbsp;&nbsp;场景：A→B→C，由于C出现性能问题，很容易导致C宕机，进而导致B调用超时，如果这时候A重复请求B，可能会对B造成积压，导致B发生故障

&nbsp;&nbsp;期望结果：C发生故障或超时的情况下，B能正常使用

<h4>解决方案：</h4>

&nbsp;&nbsp;1）  定义超时时间，当C响应过长时，B不再等待

&nbsp;&nbsp;2）  B提供一个C不可用时的备用方案

---
<h3>HOW？怎样使用服务熔断</h3>

    Sentinel/alibaba
    Hystrix/spring-clound-Netflix
    Resilience4j/java8

<h4>1、Hystrix的基本使用</h4>

&nbsp;&nbsp;入jar：org.springframework.cloud:spring-cloud-starter-netflix-hystrix

&nbsp;&nbsp;启动类添加@EnableCircuitBreaker等价于@EnableHystrix
<h4>2、Hystrix的工作原理与高级使用</h4>

<h5>&nbsp;&nbsp;2.1.Hystrix如何隔离服务调用</h5>

&nbsp;&nbsp;&nbsp;&nbsp;Hystrix线程池（默认10个）

&nbsp;&nbsp;&nbsp;&nbsp;带有@HystrixCommand的方法被调用时，会加入到线程池中处理

&nbsp;&nbsp;&nbsp;&nbsp;弊端：当C服务性能故障时，B服务线程池被占满，导致B服务的其他@HystrixCommand 无法执行

&nbsp;&nbsp;&nbsp;&nbsp;解决方案：单独为@HystrixCommand方法提供线程池----舱壁模式（Bulkhead）

&nbsp;&nbsp;&nbsp;&nbsp;设置@HystrixCommand 的threadPoolKey

&nbsp;&nbsp;2.2.Hystrix如何工作（标红部分为可自定义项）

&nbsp;&nbsp;&nbsp;&nbsp;1) 调用异常时，开启时间窗（默认为10秒）

&nbsp;&nbsp;&nbsp;&nbsp;2) 时间窗内，统计调用次数，是否达到最小请求数（默认20次）

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a) 未达到：则重置统计信息，回到第1步

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b) 达到：统计失败百分比是否达到阈值（默认50%）

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;i. 达到：熔断，关闭服务

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ii.未达到：重置统计信息，回到第1步

&nbsp;&nbsp;&nbsp;&nbsp;3)  熔断后，开启活动窗口（默认5秒），每隔5秒重试请求

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a)成功，重置断路器，回到第1步

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b)失败，回到第三步

&nbsp;&nbsp;&nbsp;&nbsp;![hystrix-config(2)](https://github.com/NewerForGitHub/hystrixstudy/blob/master/sc-api/src/main/img/hystrix-config(2).png)

<h5>&nbsp;&nbsp;2.3.如何控制Hystrix的行为</h5>
<h4/>3、Feign和Hystrix</h4>

<h5>&nbsp;&nbsp;3.0. feign启用Hystrix</h5>

&nbsp;&nbsp;&nbsp;&nbsp;![hystrix-config](https://github.com/NewerForGitHub/hystrixstudy/blob/master/sc-api/src/main/img/hystrix-config.png)

&nbsp;&nbsp;&nbsp;&nbsp;3.1. @FeignClient注解提供了fallback属性，支持服务降级

&nbsp;&nbsp;&nbsp;&nbsp;3.2. @FeignClient注解提供了fallbackFactory属性（优先级低于fallback属性），支持自定义降级处理

&nbsp;&nbsp;&nbsp;&nbsp;3.3. 全局设置hystrix策略：

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hystrix.command.default.XXXXXX(XXXXXX为@HystrixCommand中可设置的属性名)

&nbsp;&nbsp;&nbsp;&nbsp;针对某个Feign调用设置hystrix策略：

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hystrix.command.[HystrixCommandKey].XXXXXX

&nbsp;&nbsp;&nbsp;&nbsp;![hystrix-command](https://github.com/NewerForGitHub/hystrixstudy/blob/master/sc-api/src/main/img/hystrix-command.png)

<h5>&nbsp;&nbsp;3.4.扩展说明：</h5>

&nbsp;&nbsp;&nbsp;&nbsp;超时时间配置
<h3>4、Hystrix监控</h3>

&nbsp;&nbsp;&nbsp;&nbsp;引入actuator包：org.springframework.boot:spring-boot-starter-actuator

&nbsp;&nbsp;&nbsp;&nbsp;引入hystrix-dashboard包：org.springframework.boot:spring-cloud-starter-netflix-hystrix-dashboard

&nbsp;&nbsp;&nbsp;&nbsp;图形界面：localhost:81/hystrix

&nbsp;&nbsp;&nbsp;&nbsp;监控服务：监控localhost:54001/actuatior/hystrix.stream端点

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![hystrix-dashboard](https://github.com/NewerForGitHub/hystrixstudy/blob/master/sc-api/src/main/img/hystrix-dashboard.png)

&nbsp;&nbsp;&nbsp;&nbsp;聚合服务：监控/turbine.stream端点，自动加载/hystrix.stream