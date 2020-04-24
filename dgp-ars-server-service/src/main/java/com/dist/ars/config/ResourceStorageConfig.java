package com.dist.ars.config;


import com.dist.base.utils.resourceStorage.IResourceStorage;
import com.dist.base.utils.resourceStorage.MongoResourceStorage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-配置类：mongo资源存储服务的配置
 */
@Configuration
public class ResourceStorageConfig {

    @Bean("mongoResourceStorage")
    public IResourceStorage getMongoResourceStorage(@Qualifier("secondGridFsTemplate") GridFsTemplate gridFsTemplate){
        MongoResourceStorage mongoResourceStorage = new MongoResourceStorage();
        mongoResourceStorage.setGridFsTemplate(gridFsTemplate);

        return mongoResourceStorage;
    }
}
