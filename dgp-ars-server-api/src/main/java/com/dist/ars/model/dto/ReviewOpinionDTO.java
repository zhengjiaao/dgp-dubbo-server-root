package com.dist.ars.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：审查意见的显示实体
 */
@ApiModel(value = "审查意见的显示实体")
public class ReviewOpinionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("审查意见id")
    private Long id;
    @ApiModelProperty("问题位置")
    private String questionPosition;
    @ApiModelProperty("问题描述")
    private String questionDesc;
    @ApiModelProperty("问题位置")
    private Long reviewPointId;
    @ApiModelProperty("所属的审查要点ID")
    private Long reviewTaskId;
    @ApiModelProperty("所属的审查任务ID")
    private Date createTime;
    @ApiModelProperty("重要标记")
    private Boolean importantTag;

    // 是否具有改意见的操作权限
    private Boolean hasOperate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getReviewTaskId() {
        return reviewTaskId;
    }

    public void setReviewTaskId(Long reviewTaskId) {
        this.reviewTaskId = reviewTaskId;
    }

    public String getQuestionPosition() {
        return questionPosition;
    }

    public void setQuestionPosition(String questionPosition) {
        this.questionPosition = questionPosition;
    }

    public Long getReviewPointId() {
        return reviewPointId;
    }

    public void setReviewPointId(Long reviewPointId) {
        this.reviewPointId = reviewPointId;
    }

    public Boolean getImportantTag() {
        return importantTag;
    }

    public void setImportantTag(Boolean importantTag) {
        this.importantTag = importantTag;
    }

    public Boolean getHasOperate() {
        return hasOperate;
    }

    public void setHasOperate(Boolean hasOperate) {
        this.hasOperate = hasOperate;
    }
}
