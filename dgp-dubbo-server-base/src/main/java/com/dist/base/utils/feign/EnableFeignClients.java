package com.dist.base.utils.feign;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启Feign扫描
 *
 * @author yujx
 * @date 2019/03/27 09:09
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(FeignClientImportBeanDefinitionRegistrar.class)
public @interface EnableFeignClients {

    /**
     * 扫描包路径
     */
    String[] value();
}
