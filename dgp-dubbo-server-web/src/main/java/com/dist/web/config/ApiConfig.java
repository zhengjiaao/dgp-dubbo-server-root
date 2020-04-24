package com.dist.web.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: yujx
 * Email: yujx@dist.com.cn
 * Desc：系统-配置类：swagger 接口测试使用
 */
@Configuration
@EnableSwagger2
public class ApiConfig {
    @Bean
    public Docket adminApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                       .groupName("Admin API")
                       .forCodeGeneration(true)
                       .pathMapping("/")
                       .select()
                       .paths(paths())
                       .build()
                       .apiInfo(apiInfo())
                       .useDefaultResponseMessages(false);
    }

    private Predicate<String> paths(){
        return PathSelectors.regex("^/(?!error).*$");
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("数据中心", "https://github.com/DistX", "xxx@com.dist.com.cn");
        return new ApiInfoBuilder()
                       .title("后台管理系统")
                       .description("后台API文档")
                       .contact(contact)
                       .version("1.0")
                       .build();
    }
}
