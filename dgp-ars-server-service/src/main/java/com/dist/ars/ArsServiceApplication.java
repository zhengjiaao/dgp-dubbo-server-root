package com.dist.ars;

import com.dist.base.utils.feign.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-启动类：引入重试/异步机制/dubbo/AOP/Feign/扫描包等注解配置
         */
@EnableFeignClients(value = "com.dist.ars")
@EnableAspectJAutoProxy
@EnableRetry
@EnableAsync
@EnableScheduling
@SpringBootApplication
@ImportResource({"classpath:config/spring-dubbo-arsprovider.xml"})
@ComponentScan(basePackages = {"com.dist.ars", "com.dist.base.utils"})
public class ArsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArsServiceApplication.class, args);
    }

}

