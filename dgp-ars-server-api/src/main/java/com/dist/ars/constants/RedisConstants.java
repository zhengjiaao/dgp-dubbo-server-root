package com.dist.ars.constants;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-全局常量：Redis缓存组
 */
public final class RedisConstants {

    // 组
    static final String _PREFIX = "ars:";

    //缓存组：节点
    public static final class CacheKey {
        public static final String OPINION = _PREFIX + "opinion";
    }
}