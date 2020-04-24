package com.dist.web.config.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: yujx
 * Email: yujx@dist.com.cn
 * Desc：系统-配置类：配置Mongo多数据源-02 ,如果需要使用SpringDataMongo的Repositories则需要将注释打开
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.second")
@ConditionalOnExpression("${spring.data.mongodb.second.enabled}")
//@EnableMongoRepositories(basePackages = {"cn.x5456.mongo.second"}, mongoTemplateRef = "secondMongoTemplate")
public class SecondMongoConfigure extends MongoProperties {


    @Bean
    public MongoClientOptions mongoOptions() {
        return MongoClientOptions
                       .builder()
                       .maxConnectionIdleTime(3*60*1000)  //线程池中连接的最大空闲时间
                       .connectTimeout(10*60*1000)
                       .minConnectionsPerHost(5)  //线程池空闲时保持的最小连接数
                       .connectionsPerHost(20)   //线程池允许的最大连接数
                       .socketKeepAlive(true)
                       .build();
    }

    @Bean(name = "secondMongoDbFactory")
    @Override
    public MongoDbFactory mongoDbFactory() {
        // 有认证的初始化方法
        ServerAddress serverAddress = new ServerAddress(super.getHost(), super.getPort());
        List<MongoCredential> mongoCredentialList = new ArrayList<>();
        mongoCredentialList.add(MongoCredential.createScramSha1Credential(super.getUsername(), super.getAuthenticationDatabase(),super.getPassword().toCharArray()));
        return new SimpleMongoDbFactory(new MongoClient(serverAddress,mongoCredentialList,mongoOptions()), super.getDatabase());
        // 无认证的初始化方法
//        return new SimpleMongoDbFactory(new MongoClient(super.getHost(), super.getPort()), super.getDatabase());
    }

    @Bean(name = "secondMongoTemplate")
    @Override
    public MongoTemplate mongoTemplate(@Qualifier("secondMongoDbFactory") MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean(name = "secondGridFsTemplate")
    @Override
    public GridFsTemplate gridFsTemplate(@Qualifier("secondMongoDbFactory") MongoDbFactory mongoDbFactory,
                                         @Qualifier("secondMongoTemplate") MongoTemplate mongoTemplate) {
        return new GridFsTemplate(new GridFsMongoDbFactory(mongoDbFactory, super.getDatabase()), mongoTemplate.getConverter());
    }

}