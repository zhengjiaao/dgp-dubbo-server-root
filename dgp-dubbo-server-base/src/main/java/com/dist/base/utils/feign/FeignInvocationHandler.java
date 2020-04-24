package com.dist.base.utils.feign;

import com.dist.base.utils.ApplicationContextUtil;
import com.dist.base.utils.ObjectUtil;
import com.dist.base.utils.SPELUtil;
import com.google.common.collect.ImmutableList;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs2.JAXRS2Contract;
import feign.slf4j.Slf4jLogger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Feign动态代理处理器
 *
 * @author yujx
 * @date 2019/03/21 14:00
 */
class FeignInvocationHandler implements InvocationHandler {

    private String requestUrl;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 1、根据spel表达式获取请求的地址
        requestUrl = String.valueOf(SPELUtil.resolveExpression(requestUrl));

        // 1.1 获取代理对象的真实类属性
        Class<?> clazz = method.getDeclaringClass();

        // 获取方法的返回值
        Class<?> returnType = method.getReturnType();

        // 根据返回值的不同构造不同的Feign客户端对象
        Object target = FeignBuilderInstance.getInstanceByReturnType(returnType).target(clazz, requestUrl);

        // 获取参数的字节码属性数组
        Class<?>[] classes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            classes[i] = args[i].getClass();
        }

        // 获取代理方法
        Method proxyMethod = target.getClass().getMethod(method.getName(), classes);

        // 执行
        return proxyMethod.invoke(target, args);
    }

    FeignInvocationHandler(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    private static class FeignBuilderInstance {

        private static final Request.Options requestOptions;

        private static final Retryer.Default retryer;

        static {
            Long connectTimeout = ApplicationContextUtil.getConfigInfo("feign.connectTimeout", Long.class);
            Long readTimeout = ApplicationContextUtil.getConfigInfo("feign.readTimeout", Long.class);
            if (ObjectUtil.isNonNull(connectTimeout, readTimeout)) {
                requestOptions = new Request.Options(connectTimeout, TimeUnit.MILLISECONDS, readTimeout, TimeUnit.MILLISECONDS, true);
            } else {
                requestOptions = new Request.Options();
            }

            Long period = ApplicationContextUtil.getConfigInfo("feign.retry.period", Long.class);
            Long maxPeriod = ApplicationContextUtil.getConfigInfo("feign.retry.maxPeriod", Long.class);
            Integer maxAttempts = ApplicationContextUtil.getConfigInfo("feign.retry.maxAttempts", Integer.class);

            if (ObjectUtil.isNonNull(period, maxPeriod, maxAttempts)) {
                retryer = new Retryer.Default(period, maxPeriod, maxAttempts);
            } else {
                retryer = new Retryer.Default();
            }
        }

        // TODO: OkHttpClient不知为何，设置连接超时时间无效，暂时使用ApacheHttpClient
        private static final Feign.Builder NEED_DECODE_BUILDER = Feign.builder()
                .client(new ApacheHttpClient())
                .contract(new JAXRS2Contract())
                .encoder(new FormEncoderExt(new JacksonEncoder()))
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                // 设置连接超时和读取超时时间
                .options(requestOptions)
                // 设置重试时间和次数
                .retryer(retryer);

        private static final Feign.Builder DOES_NOT_NEED_DECODE_BUILDER = Feign.builder()
                .client(new ApacheHttpClient())
                .contract(new JAXRS2Contract())
                .encoder(new FormEncoderExt(new JacksonEncoder()))
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                // 设置连接超时和读取超时时间
                .options(requestOptions)
                // 设置重试时间和次数
                .retryer(retryer);

        // 不需要JacksonDecoder的返回值类型
        private static final List<Class> DOES_NOT_DECODE_TYPE = ImmutableList.of(
                Integer.class,
                String.class,
                Double.class,
                Boolean.class,
                Void.class,
                Character.class,
                byte[].class
        );

        /* 根据返回值类型获取feign的builder */
        static Feign.Builder getInstanceByReturnType(Class<?> returnType) {
            if (DOES_NOT_DECODE_TYPE.contains(returnType) || returnType.isPrimitive()) {
                return DOES_NOT_NEED_DECODE_BUILDER;
            }
            return NEED_DECODE_BUILDER;
        }
    }
}
