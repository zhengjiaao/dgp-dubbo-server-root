package com.dist.web.controller.ars;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dist.ars.model.dto.ReviewOpinionDTO;
import com.dist.ars.model.query.request.ReviewOpinionAddRequest;
import com.dist.ars.model.query.request.ReviewOpinionUpdateRequest;
import com.dist.ars.service.IReviewOpinionBizService;
import com.dist.base.response.ResponseData;
import com.dist.base.response.ResponseUtil;
import com.dist.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统：审查意见管理模块
 */
@RestController
@RequestMapping(value = "/rest/ars/private/reviewOpinion")
@Api(tags = {"ARS-ReviewOpinionController"}, description = "审查意见管理模块")
public class ReviewOpinionController extends BaseController {

    @Reference
    private IReviewOpinionBizService reviewOpinionBizService;

    @ApiOperation(value = "新增一个审查意见", httpMethod = "POST")
    @PostMapping(value = "/v1/reviewOpinion")
    public ResponseData addReviewOpinion(@RequestBody @ApiParam(value = "审查意见类型对应审查要点") ReviewOpinionAddRequest reviewOpinionAddRequest) {

        ReviewOpinionDTO reviewOpinion = reviewOpinionBizService.saveReviewOpinion(reviewOpinionAddRequest);
        return ResponseUtil.success(reviewOpinion);
    }

    @ApiOperation(value = "更新审查意见", httpMethod = "PUT")
    @PutMapping(value = "/v1/reviewOpinion")
    public ResponseData updateReviewOpinion(@RequestBody @ApiParam(value = "不参与更新字段，则不传") ReviewOpinionUpdateRequest reviewOpinionUpdateRequest) {

        return ResponseUtil.success(reviewOpinionBizService.updateReviewOpinion(reviewOpinionUpdateRequest));
    }

    @ApiOperation(value = "删除一个审查意见", httpMethod = "DELETE")
    @DeleteMapping(value = "/v1/reviewOpinion/{reviewOpinionId}")
    public ResponseData deleteReviewOpinion(@PathVariable @ApiParam(value = "审查意见主键ID") Long reviewOpinionId) {

        reviewOpinionBizService.deleteReviewOpinion(reviewOpinionId);
        return ResponseUtil.success(true);
    }

}
