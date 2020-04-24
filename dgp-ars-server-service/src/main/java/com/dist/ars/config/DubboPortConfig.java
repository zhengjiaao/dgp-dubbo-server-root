package com.dist.ars.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-配置类：Dubbo端口配置类
 */
@Configuration
public class DubboPortConfig {
    @Bean
    public ServletListenerRegistrationBean<DubboServerListener> dubboServerServletListenerRegistrationBean(){
        ServletListenerRegistrationBean<DubboServerListener> dubboServerServletListener = new ServletListenerRegistrationBean<DubboServerListener>();
        dubboServerServletListener.setListener(new DubboServerListener());
        return dubboServerServletListener;
    }
}
