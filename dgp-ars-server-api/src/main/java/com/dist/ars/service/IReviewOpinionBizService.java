package com.dist.ars.service;

import com.dist.ars.model.dto.ReviewOpinionDTO;
import com.dist.ars.model.query.request.ReviewOpinionAddRequest;
import com.dist.ars.model.query.request.ReviewOpinionUpdateRequest;

import java.util.List;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统：审查意见管理业务接口
 */
public interface IReviewOpinionBizService {

    /**
     * 新增审查意见实体
     */
    ReviewOpinionDTO saveReviewOpinion(ReviewOpinionAddRequest reviewOpinionAddRequest);


    /**
     * 更新审查意见实体
     */
    ReviewOpinionDTO updateReviewOpinion(ReviewOpinionUpdateRequest reviewOpinionUpdateRequest);

    /**
     * 删除一个审查意见实体
     */
    void deleteReviewOpinion(Long reviewOpinionId);

}
