package com.dist.base.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.stereotype.Component;

/**
 * 针对 Spring 实现某些特殊逻辑时，支持 SPEL 表达式
 * 
 * @author x5456
 */
@Component
public class SPELUtil implements BeanFactoryAware, BeanClassLoaderAware {

    private static BeanFactory beanFactory;
    private static BeanExpressionResolver resolver;
    private static BeanExpressionContext expressionContext;

    /**
     * 解析 SPEL
     *
     * @param value
     * @return
     */
    public static Object resolveExpression(String value){
        if (value.startsWith("${") && value.endsWith("}")) {
            String resolvedValue = resolve(value);
            return SPELUtil.resolver.evaluate(resolvedValue, SPELUtil.expressionContext);
        }
        return value;
    }

    /**
     * 解析 ${}
     * @param value
     * @return
     */
    private static String resolve(String value){
        if (SPELUtil.beanFactory != null && SPELUtil.beanFactory instanceof ConfigurableBeanFactory) {
            return ((ConfigurableBeanFactory) SPELUtil.beanFactory).resolveEmbeddedValue(value);
        }
        return value;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        SPELUtil.resolver = new StandardBeanExpressionResolver(classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SPELUtil.beanFactory = beanFactory;
        if(beanFactory instanceof ConfigurableListableBeanFactory){
            SPELUtil.resolver = ((ConfigurableListableBeanFactory) beanFactory).getBeanExpressionResolver();
            SPELUtil.expressionContext = new BeanExpressionContext((ConfigurableListableBeanFactory) beanFactory, null);
        }
    }

}