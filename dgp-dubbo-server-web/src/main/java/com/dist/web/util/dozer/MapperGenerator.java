package com.dist.web.util.dozer;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：系统-工具类：dozer对象之间的转换接口实现类
 */
@Component
@Lazy(true)
public class MapperGenerator implements IGenerator {

    @Autowired
    protected Mapper dozerMapper;

    @Override
    public <T, S> T convert(S s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        return this.dozerMapper.map(s, clz);
    }

    @Override
    public <T, S> List<T> convert(List<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        for (S vs : s) {
            list.add(this.dozerMapper.map(vs, clz));
        }
        return list;
    }

    @Override
    public <T, S> Set<T> convert(Set<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        Set<T> set = new HashSet<T>();
        for (S vs : s) {
            set.add(this.dozerMapper.map(vs, clz));
        }
        return set;
    }

    @Override
    public <T, S> T[] convert(S[] s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(clz, s.length);
        for (int i = 0; i < s.length; i++) {
            arr[i] = this.dozerMapper.map(s[i], clz);
        }
        return arr;
    }
}