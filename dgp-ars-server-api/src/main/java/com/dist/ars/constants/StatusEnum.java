package com.dist.ars.constants;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-保存全局状态枚举：目录节点等级、删除状态
 */
public abstract class StatusEnum {

    /**
     * 目录节点等级
     */
    public static enum CatalogNodeLevelEnum {
        ROOT(0, "根节点"),
        CATALOG(1, "目录节点"),
        LEAF_NODE(2, "叶子节点");

        private Integer code;
        private String desc;

        CatalogNodeLevelEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer code() {
            return this.code;
        }
        public String desc() {
            return this.desc;
        }
    }

    /**
     * 删除状态
     */
    public enum DelStatusEnum {

        NOT_DELETED("0", "未删除状态"),
        DELETED("1", "删除状态");

        private String code;
        private String desc;

        DelStatusEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String code() {
            return this.code;
        }
        public String desc() {
            return this.desc;
        }
    }

}
