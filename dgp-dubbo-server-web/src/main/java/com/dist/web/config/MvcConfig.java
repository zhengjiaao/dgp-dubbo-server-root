package com.dist.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: yujx
 * Email: yujx@dist.com.cn
 * Desc：系统-配置类：Mvc配置 swagger
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 启动时页面重定向到swagger页面
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html");
    }
}