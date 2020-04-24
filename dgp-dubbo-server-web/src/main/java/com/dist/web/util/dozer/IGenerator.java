package com.dist.web.util.dozer;

import java.util.List;
import java.util.Set;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：系统-工具类：dozer对象之间的转换接口类
 */
public interface IGenerator {

    /**
     * @Description: 单个对象的深度复制及类型转换，vo/domain , po
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     */
    <T, S> T convert(S s, Class<T> clz);

    /**
     * @Description: list深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     */
    <T, S> List<T> convert(List<S> s, Class<T> clz);

    /**
     * @Description: set深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     */
    <T, S> Set<T> convert(Set<S> s, Class<T> clz);

    /**
     * @Description: 数组深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     */
    <T, S> T[] convert(S[] s, Class<T> clz);

}