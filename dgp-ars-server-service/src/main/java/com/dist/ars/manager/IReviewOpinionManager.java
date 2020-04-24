package com.dist.ars.manager;

import com.dist.ars.model.entity.ars.ReviewOpinion;

import java.util.List;
import java.util.Map;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统：审查意见管理接口-不参与业务处理
 */
public interface IReviewOpinionManager {


    /**
     * 添加一个审查意见实体
     */
    ReviewOpinion saveReviewOpinion(ReviewOpinion reviewOpinion);

    /**
     * 根据主键ID删除一个审查意见实体
     */
    void deleteReviewOpinion(Long reviewOpinionId);


    /**
     * 根据主键ID返回审查意见实体
     */
    ReviewOpinion findById(Long id);



}
