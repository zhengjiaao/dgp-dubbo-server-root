package com.dist.ars.model.query.request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：审查意见添加请求实体
 */
@ApiModel(value = "审查意见添加请求实体")
public class ReviewOpinionAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("问题位置")
    private String questionPosition;
    @ApiModelProperty("问题描述")
    private String questionDesc;
    @ApiModelProperty("所属的审查要点ID")
    private Long reviewPointId;
    @ApiModelProperty("所属的审查任务ID")
    private Long reviewTaskId;
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

    public Long getReviewTaskId() {
        return reviewTaskId;
    }

    public void setReviewTaskId(Long reviewTaskId) {
        this.reviewTaskId = reviewTaskId;
    }

    public Boolean getImportantTag() {
        return importantTag;
    }

    public void setImportantTag(Boolean importantTag) {
        this.importantTag = importantTag;
    }

    public Long getReviewPointId() {
        return reviewPointId;
    }

    public void setReviewPointId(Long reviewPointId) {
        this.reviewPointId = reviewPointId;
    }
}
