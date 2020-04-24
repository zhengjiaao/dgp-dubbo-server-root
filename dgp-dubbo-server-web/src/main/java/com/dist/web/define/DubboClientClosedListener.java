package com.dist.web.define;

import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.remoting.transport.netty.NettyClient;
import com.alibaba.dubbo.rpc.RpcContext;
import org.jboss.netty.channel.ChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：系统 ： 优雅关闭客户端连接
 */
@Component
public class DubboClientClosedListener implements ApplicationListener<ContextClosedEvent> {
    private static Logger LOG = LoggerFactory.getLogger(DubboClientClosedListener.class);
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {

        if(RpcContext.getContext().getRemoteHost() == null){
            LOG.info("-------------没有需要关闭的资源-----------");
            return;
        }
        LOG.info("before spring context is closed, META-INF.META-INF config destroy all...");
        ProtocolConfig.destroyAll();
        // 用反射释放NettyClient所占用的资源, 以避免不能优雅shutdown的问题
        releaseNettyClientExternalResources();
    }

    private void releaseNettyClientExternalResources() {
        try {
            Field field = NettyClient.class.getDeclaredField("channelFactory");
            field.setAccessible(true);
            ChannelFactory channelFactory = (ChannelFactory) field.get(NettyClient.class);
            channelFactory.releaseExternalResources();
            field.setAccessible(false);
            LOG.info("Release NettyClient's external resources");
        } catch (Exception e) {
            LOG.error("Release NettyClient's external resources error", e);
        }
    }
}
