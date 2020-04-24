package com.dist.ars.config;

import com.alibaba.dubbo.registry.dubbo.DubboRegistryFactory;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-配置类：解决tomcat reload dubbo未释放问题
 */
public class DubboServerListener implements ServletContextListener{
    /** 日志记录 */
    private Logger logger = LoggerFactory.getLogger(DubboServerListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("销毁dubbo实例中....");
        DubboRegistryFactory.destroyAll();
        DubboProtocol.getDubboProtocol().destroy();
        logger.info("销毁dubbo服务完成！");
    }
}
