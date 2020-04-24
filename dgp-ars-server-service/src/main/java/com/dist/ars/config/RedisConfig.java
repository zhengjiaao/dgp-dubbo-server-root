package com.dist.ars.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-配置类：Redis缓存配置
 */
@Configuration
@EnableCaching //开启注解
public class RedisConfig {

    /*@Bean("longRedisTemplate")
    public RedisTemplate<String, Long> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Long.class));
        return template;
    }*/

    /**************** 注解缓存失效时间配置 *******************/
    /**
     * springboot 1.XX版本的配置redis 缓存失效时间
     * @param redisTemplate
     */
    @Bean
    public RedisCacheManager myRedisCacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //默认使用cacheNames作为key的前缀
        cacheManager.setUsePrefix(true);
        //设置缓存过期时间（秒）
        cacheManager.setDefaultExpiration(60*30);
        return cacheManager;
    }

    @Bean("longRedisTemplate")
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        // 定义key序列化方式
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        // 定义value的序列化方式
        Jackson2JsonRedisSerializer valueSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        valueSerializer.setObjectMapper(om);

        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 对链表类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, String> listOperations(@Qualifier("longRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForList();
    }

}