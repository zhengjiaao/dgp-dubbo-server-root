package com.dist.web.config.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: yujx
 * Email: yujx@dist.com.cn
 * Desc：系统-配置类：配置Mongo多数据源-01 ,如果需要使用SpringDataMongo的Repositories则需要将注释打开
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.first")
public class FirstMongoConfigure extends MongoProperties {

    @Primary
    @Bean(name = "firstMongoDbFactory")
    public MongoDbFactory mongoDbFactory() {
        // 有认证的初始化方法
        ServerAddress serverAddress = new ServerAddress(super.getHost(), super.getPort());
        List<MongoCredential> mongoCredentialList = new ArrayList<>();
        mongoCredentialList.add(MongoCredential.createScramSha1Credential(
                super.getUsername(), super.getAuthenticationDatabase(),super.getPassword().toCharArray()));
        return new SimpleMongoDbFactory(new MongoClient(serverAddress,mongoCredentialList), super.getDatabase());
        // 无认证的初始化方法
//        return new SimpleMongoDbFactory(new MongoClient(super.getHost(), super.getPort()), super.getDatabase());
    }

    @Primary
    @Bean(name = "firstMongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("firstMongoDbFactory") MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

//    @Primary
//    @Bean(name = "mongoTemplate")
//    public MongoTemplate defaultMongoTemplate(@Qualifier("firstMongoDbFactory") MongoDbFactory mongoDbFactory) {
//        return new MongoTemplate(mongoDbFactory);
//    }

    @Primary
    @Bean(name = "firstGridFsTemplate")
    public GridFsTemplate gridFsTemplate(@Qualifier("firstMongoDbFactory") MongoDbFactory mongoDbFactory,
                                         @Qualifier("firstMongoTemplate") MongoTemplate mongoTemplate) {
        return new GridFsTemplate(new GridFsMongoDbFactory(
                mongoDbFactory,super.getDatabase()), mongoTemplate.getConverter());
    }
}