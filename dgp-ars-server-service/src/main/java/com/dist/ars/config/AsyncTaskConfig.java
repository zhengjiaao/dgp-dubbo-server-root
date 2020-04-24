package com.dist.ars.config;

import com.dist.base.utils.ObjectUtil;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: yujx
 * Email: yujx@dist.com.cn
 * Desc：辅助审查系统-配置类：异步执行定时任务
 */
@Configuration
public class AsyncTaskConfig extends AsyncConfigurerSupport implements SchedulingConfigurer {
    //线程池线程数量
    private final static int corePoolSize = Runtime.getRuntime().availableProcessors() * 5;

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();// 初始化线程池
        scheduler.setPoolSize(corePoolSize);// 线程池容量
        scheduler.setDaemon(true);  // 设置守护线程
        return scheduler;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setTaskDecorator(new MdcTaskDecorator());
        threadPool.setCorePoolSize(corePoolSize);
        threadPool.setMaxPoolSize(corePoolSize);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(60 * 15);
        threadPool.setThreadNamePrefix("AsyncThread-");
        threadPool.initialize();
        return threadPool;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler());
    }

    private class MdcTaskDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable runnable) {

            Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
            return () -> {
                try {
                    if (ObjectUtil.isNonNull(copyOfContextMap)) {
                        MDC.setContextMap(copyOfContextMap);
                    }
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            };

        }
    }
}