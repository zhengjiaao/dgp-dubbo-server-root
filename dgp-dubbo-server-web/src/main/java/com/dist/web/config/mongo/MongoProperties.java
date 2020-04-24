package com.dist.web.config.mongo;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: yujx
 * Email: yujx@dist.com.cn
 * Desc：系统-Mongo：Mongo属性类
 */
public abstract class MongoProperties {

    private String host;
    private int port;
    private String database;
    private String authenticationDatabase;
    private String username;
    private String password;

    public abstract MongoDbFactory mongoDbFactory();

    public abstract MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory);

    public abstract GridFsTemplate gridFsTemplate(MongoDbFactory mongoDbFactory, MongoTemplate mongoTemplate);

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getAuthenticationDatabase() {
        return authenticationDatabase;
    }

    public void setAuthenticationDatabase(String authenticationDatabase) {
        this.authenticationDatabase = authenticationDatabase;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}