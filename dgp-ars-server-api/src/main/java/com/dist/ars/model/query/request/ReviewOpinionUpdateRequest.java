package com.dist.ars.model.query.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：审查意见更新请求实体
 */
@ApiModel(value = "审查意见更新请求实体")
public class ReviewOpinionUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("审查意见id")
    private Long id;
    @ApiModelProperty("问题位置")
    private String questionPosition;
    @ApiModelProperty("问题描述")
    private String questionDesc;
    @ApiModelProperty("重要标记")
    private Boolean importantTag;

    public String getQuestionPosition() {
        return questionPosition;
    }

    public void setQuestionPosition(String questionPosition) {
        this.questionPosition = questionPosition;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getImportantTag() {
        return importantTag;
    }

    public void setImportantTag(Boolean importantTag) {
        this.importantTag = importantTag;
    }
}
