package com.dist.ars.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dist.ars.constants.RedisConstants;
import com.dist.ars.exceptions.IllegalParameterException;
import com.dist.ars.manager.IReviewOpinionManager;
import com.dist.ars.model.dto.ReviewOpinionDTO;
import com.dist.ars.model.entity.ars.ReviewOpinion;
import com.dist.ars.model.query.request.ReviewOpinionAddRequest;
import com.dist.ars.model.query.request.ReviewOpinionUpdateRequest;
import com.dist.ars.util.dozer.IGenerator;
import com.dist.base.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统：审查意见管理业务接口实现类
 */
@Service
@Transactional
@CacheConfig(cacheNames = RedisConstants.CacheKey.OPINION)
public class ReviewOpinionBizServiceBizImpl implements IReviewOpinionBizService {

    @Autowired
    private IGenerator dozer;

    @Autowired
    private IReviewOpinionManager reviewOpinionManager;


    /**
     * 新建审查意见
     * CacheEvict:清空redis OPINION组下的所有缓存,忽略key
     */
    @CacheEvict(value = RedisConstants.CacheKey.OPINION, allEntries = true)
    @Override
    public ReviewOpinionDTO saveReviewOpinion(ReviewOpinionAddRequest reviewOpinionAddRequest) {
        ReviewOpinion reviewOpinion = dozer.convert(reviewOpinionAddRequest, ReviewOpinion.class);
        reviewOpinion.setCreateTime(new Date());
        reviewOpinion = reviewOpinionManager.saveReviewOpinion(reviewOpinion);
        return dozer.convert(reviewOpinion, ReviewOpinionDTO.class);
    }


    /**
     * 更新审查意见实体
     * CacheEvict:清空redis OPINION组下的所有缓存,忽略key
     */
    @CacheEvict(value = RedisConstants.CacheKey.OPINION, allEntries = true)
    @Override
    public ReviewOpinionDTO updateReviewOpinion(ReviewOpinionUpdateRequest reviewOpinionUpdateRequest) {
        ReviewOpinion reviewOpinion = reviewOpinionManager.findById(reviewOpinionUpdateRequest.getId());
        if (ObjectUtil.isAnyNull(reviewOpinion)) {
            throw new IllegalParameterException("输入的审查意见id不存在");
        }

        if (ObjectUtil.isNonNull(reviewOpinionUpdateRequest.getQuestionDesc())) {
            reviewOpinion.setQuestionDesc(reviewOpinionUpdateRequest.getQuestionDesc());
        }
        if (ObjectUtil.isNonNull(reviewOpinionUpdateRequest.getQuestionPosition())) {
            reviewOpinion.setQuestionPosition(reviewOpinionUpdateRequest.getQuestionPosition());
        }
        if (ObjectUtil.isNonNull(reviewOpinionUpdateRequest.getImportantTag())) {
            reviewOpinion.setImportantTag(reviewOpinionUpdateRequest.getImportantTag());
        }

        reviewOpinion = reviewOpinionManager.saveReviewOpinion(reviewOpinion);
        return dozer.convert(reviewOpinion, ReviewOpinionDTO.class);
    }

    /**
     * 删除审查意见
     * CacheEvict:清空redis OPINION组下的所有缓存,忽略key
     */
    @CacheEvict(value = RedisConstants.CacheKey.OPINION, allEntries = true)
    @Override
    public void deleteReviewOpinion(Long reviewOpinionId) {

        reviewOpinionManager.deleteReviewOpinion(reviewOpinionId);
    }

}
