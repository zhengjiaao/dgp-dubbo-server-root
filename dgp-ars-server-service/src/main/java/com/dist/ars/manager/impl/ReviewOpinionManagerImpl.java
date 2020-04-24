package com.dist.ars.manager.impl;

import com.dist.ars.dao.ars.ReviewOpinionRepository;
import com.dist.ars.manager.IReviewOpinionManager;
import com.dist.ars.model.entity.ars.ReviewOpinion;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统：审查意见管理接口实现类-不参与业务处理
 */
@Component
@Transactional
public class ReviewOpinionManagerImpl implements IReviewOpinionManager {

    @Resource
    private ReviewOpinionRepository reviewOpinionRepository;

    /**
     * 添加一个审查意见实体
     */
    @Override
    public ReviewOpinion saveReviewOpinion(ReviewOpinion opinion) {
        return reviewOpinionRepository.save(opinion);
    }

    /**
     * 删除一个审查意见实体
     */
    @Override
    public void deleteReviewOpinion(Long reviewOpinionId) {
        reviewOpinionRepository.delete(reviewOpinionId);
    }

    /**
     * 根据主键ID返回审查意见实体
     */
    @Override
    public ReviewOpinion findById(Long id) {
        return reviewOpinionRepository.findById(id);
    }

}
