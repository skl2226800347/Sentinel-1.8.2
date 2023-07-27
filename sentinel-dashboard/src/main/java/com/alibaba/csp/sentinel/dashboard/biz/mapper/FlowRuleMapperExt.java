package com.alibaba.csp.sentinel.dashboard.biz.mapper;

import com.alibaba.csp.sentinel.dashboard.biz.domain.query.FlowRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.FlowRule;

import java.util.List;

public interface FlowRuleMapperExt extends FlowRuleMapper{

    /**
     * 量控制规则分页查询接口
     * @param flowRulePaginationQuery 入参
     * @return 结果
     */
    List<FlowRule> selectFlowRuleWithPaging(FlowRulePaginationQuery flowRulePaginationQuery);

    /**
     * 查询条数
     * @param flowRulePaginationQuery 入参
     * @return 条数
     */
    Integer countFlowRuleWithPaging(FlowRulePaginationQuery flowRulePaginationQuery);

}
