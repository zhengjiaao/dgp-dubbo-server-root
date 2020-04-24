package com.dist.ars.config.multipleDatasource;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-配置类：将设置参数的druid的数据源注册到IOC容器中
 */
@Configuration
public class DBConfig {

    @Primary
    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.first")
    public DataSource druid() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置Druid监控
     * 1.配置管理后台的Servlet
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        //登录后台时的用户名
        initParams.put("loginUsername", "admin");
        //登录后台时的密码
        initParams.put("loginPassword", "admin");
        //默认就是允许所有的访问
        initParams.put("allow", "");
        //拒绝
        initParams.put("deny", "");
        servletRegistrationBean.setInitParameters(initParams);
        return servletRegistrationBean;
    }

    /**
     * 2.配置一个web监控的过滤器
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        //不拦截的请求
        initParams.put("exclusions", "*.js,*.css,/druid/*");
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        return filterRegistrationBean;
    }

}
