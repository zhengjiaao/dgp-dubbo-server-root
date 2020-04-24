package com.dist.ars.util.dozer;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-工具类：dozer对象之间的转换接口类
 */
public interface IGenerator {

    /**
     * @param s   数据对象
     * @param clz 复制目标类型
     * @return
     * @Description: 单个对象的深度复制及类型转换，vo/domain , po
     */
    <T, S> T convert(S s, Class<T> clz);

    /**
     * @param s   数据对象
     * @param clz 复制目标类型
     * @return
     * @Description: list深度复制
     */
    <T, S> List<T> convert(List<S> s, Class<T> clz);

    /**
     * @param s   数据对象
     * @param clz 复制目标类型
     * @return
     * @Description: set深度复制
     */
    <T, S> Set<T> convert(Set<S> s, Class<T> clz);

    /**
     * @param s   数据对象
     * @param clz 复制目标类型
     * @return
     * @Description: 数组深度复制
     */
    <T, S> T[] convert(S[] s, Class<T> clz);

    /**
     * map转对象
     *
     * @param mapList
     * @param beanClass
     * @param <T>
     * @return
     */
    <T> List<T> mapConvert2Obj(List<? extends Map<String, ?>> mapList, Class<T> beanClass);


    /**
     * 对象转map
     *
     * @param o
     * @return
     */
    Map<String, Object> objConvert2Map(Object o);


    /**
     * 对象之间转换，忽略对象中的类型，根据name进行映射
     *
     * @param s
     * @param clz
     * @param <T>
     * @param <S>
     * @return
     */
    <T, S> T convertIgnoreType(S s, Class<T> clz);

    <T, S> List<T> convertIgnoreType(List<S> s, Class<T> clz);

    <T, S> Set<T> convertIgnoreType(Set<S> s, Class<T> clz);


}