package com.dist.web;

import com.dist.base.server.EmbeddedZooKeeper;
import com.dist.base.utils.CacheFile;
import com.dist.base.utils.feign.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：系统-启动类：异步机制/dubbo/Feign/定时任务/扫描包等注解配置
 */
@EnableFeignClients("com.dist.web")
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = {"com.dist.web", "com.dist.base.utils"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
@ImportResource({"classpath:config/spring-dubbo-consumer.xml"})
public class ConsumerApplication {
    private final static String ZK_PORT_PATTERN = "[0-9]+";

    public static void main(String[] args) {
        if(args.length > 0 && args[0].matches(ZK_PORT_PATTERN)){
             new EmbeddedZooKeeper(2181, false).start();
        }
        SpringApplication.run(ConsumerApplication.class, args);
    }

    //缓存
    @Bean
    public CacheFile cacheFile(){
        return new CacheFile();
    }

}
