package com.dist.ars.dao.ars;

import com.dist.ars.model.entity.ars.ReviewOpinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统：审查意见实体管理接口-JPA接口层：参与数据库交互操作-增删改查
 */
public interface ReviewOpinionRepository extends JpaRepository<ReviewOpinion, Long>, JpaSpecificationExecutor<ReviewOpinion> {

    /**
     * 根据id获取单个审查意见实体
     *
     * @param id 审查意见id
     * @return 审查意见实体
     */
    ReviewOpinion findById(Long id);


}
