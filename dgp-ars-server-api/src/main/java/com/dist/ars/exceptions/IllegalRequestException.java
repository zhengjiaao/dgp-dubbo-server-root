package com.dist.ars.exceptions;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-22 15:30
 * Author: yinxp
 * Email: yinxp@dist.com.cn
 * Desc：辅助审查系统-全局异常：非法请求异常
 */
public class IllegalRequestException extends RuntimeException {

    public IllegalRequestException() {
        super();
    }

    public IllegalRequestException(String s) {
        super(s);
    }
}
