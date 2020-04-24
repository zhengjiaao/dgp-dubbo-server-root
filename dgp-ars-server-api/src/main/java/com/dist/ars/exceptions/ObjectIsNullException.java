package com.dist.ars.exceptions;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-22 15:30
 * Author: yinxp
 * Email: yinxp@dist.com.cn
 * Desc：辅助审查系统-全局异常：对象为空异常
 */
public class ObjectIsNullException extends RuntimeException {

    public ObjectIsNullException() {
        super();
    }

    public ObjectIsNullException(String s) {
        super(s);
    }
}
