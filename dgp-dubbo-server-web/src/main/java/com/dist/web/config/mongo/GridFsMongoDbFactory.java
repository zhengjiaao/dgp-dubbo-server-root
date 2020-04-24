package com.dist.web.config.mongo;

import com.mongodb.DB;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.util.Assert;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: yujx
 * Email: yujx@dist.com.cn
 * Desc：系统-Mongo：GridFs工厂类
 */
public class GridFsMongoDbFactory implements MongoDbFactory {

    private final MongoDbFactory mongoDbFactory;
    private final String gridFsDatabase;

    GridFsMongoDbFactory(MongoDbFactory mongoDbFactory, String gridFsDatabase) {
        Assert.notNull(mongoDbFactory, "MongoDbFactory must not be null");
        Assert.notNull(gridFsDatabase, "GridFsDatabase must not be null");
        this.mongoDbFactory = mongoDbFactory;
        this.gridFsDatabase = gridFsDatabase;
    }

    public DB getDb() throws DataAccessException {
        Assert.notNull(gridFsDatabase, "GridFsDatabase must not be null");
        return this.mongoDbFactory.getDb(gridFsDatabase);
    }

    public DB getDb(String dbName) throws DataAccessException {
        return this.mongoDbFactory.getDb(dbName);
    }

    public PersistenceExceptionTranslator getExceptionTranslator() {
        return this.mongoDbFactory.getExceptionTranslator();
    }
}