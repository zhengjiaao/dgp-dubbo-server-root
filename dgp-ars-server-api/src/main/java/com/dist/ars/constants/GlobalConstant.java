package com.dist.ars.constants;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-全局常量：文件类型和文件格式
 */
public final class GlobalConstant {

    public static final String ZIP = "zip";

    // 图片格式后缀
    public final static List<String> IMG_EXTENSION_LIST;

    // 数据源扩展列表
    public final static List<String> DATA_SOURCE_EXTENSION_LIST;

    // 文本文件格式后缀
    public final static List<String> TEXT_EXTENSION_LIST;

    public final static Map<String, String> OFFICE_ONLINE_PROPERTIES;

    static {
        IMG_EXTENSION_LIST = ImmutableList.of("png", "jpg", "jpeg");
        DATA_SOURCE_EXTENSION_LIST = ImmutableList.of("zip", "mdb");
        TEXT_EXTENSION_LIST = ImmutableList.of("ini", "txt");

        ImmutableMap.Builder<String, String> officeOnlinePropertiesBuilder = ImmutableMap.builder();
        officeOnlinePropertiesBuilder.put("doc", "wv/wordviewerframe.aspx?WOPISrc=");
        officeOnlinePropertiesBuilder.put("docm", "wv/wordviewerframe.aspx?WOPISrc=");
        officeOnlinePropertiesBuilder.put("docx", "wv/wordviewerframe.aspx?WOPISrc=");
        officeOnlinePropertiesBuilder.put("dot", "wv/wordviewerframe.aspx?WOPISrc=");
        officeOnlinePropertiesBuilder.put("dotm", "wv/wordviewerframe.aspx?WOPISrc=");

        officeOnlinePropertiesBuilder.put("pdf", "wv/wordviewerframe.aspx?PdfMode=1&WOPISrc=");

        OFFICE_ONLINE_PROPERTIES = officeOnlinePropertiesBuilder.build();
    }

    private GlobalConstant() {
    }
}
