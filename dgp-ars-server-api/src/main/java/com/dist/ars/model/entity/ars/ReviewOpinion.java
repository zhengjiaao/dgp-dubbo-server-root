package com.dist.ars.model.entity.ars;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：针对审查要点给出的审查意见
 */
@Entity
@Table(name = "ars_review_opinion")
@SequenceGenerator(name = "ID_SEQ", sequenceName = "SEQ_ARS_HIBERNATE", allocationSize = 1)
public class ReviewOpinion implements Serializable {

    private static final long serialVersionUID = 1L;

    // 审查意见id
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ")
    private Long id;

    // 问题位置
    @Column(length = 4000)
    private String questionPosition;

    // 问题描述
    @Column(length = 4000)
    private String questionDesc;

    // 所属的审查要点id  @see ReviewPoint.id
    private Long reviewPointId;

    // 所属的审查任务id  @see ReviewTask.id
    private Long reviewTaskId;

    // 创建时间
    private Date createTime;

    // 重要标记
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean importantTag;

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
