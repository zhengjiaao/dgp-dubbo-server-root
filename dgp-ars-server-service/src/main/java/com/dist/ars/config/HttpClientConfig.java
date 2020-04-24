package com.dist.ars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-配置类：Http客户端配置
 */
@Configuration
public class HttpClientConfig {

    @Bean
    public RestTemplate restTemplate(){
        OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory();
        okHttp3ClientHttpRequestFactory.setConnectTimeout(600000);
        okHttp3ClientHttpRequestFactory.setReadTimeout(600000);
        okHttp3ClientHttpRequestFactory.setWriteTimeout(600000);

        return new RestTemplate(okHttp3ClientHttpRequestFactory);
    }

    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
    }
}
