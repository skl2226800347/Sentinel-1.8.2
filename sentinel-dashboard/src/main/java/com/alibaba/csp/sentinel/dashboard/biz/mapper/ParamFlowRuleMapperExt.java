package com.alibaba.csp.sentinel.dashboard.biz.mapper;

import com.alibaba.csp.sentinel.dashboard.biz.domain.query.ParamFlowRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.ParamFlowRule;

import java.util.List;

public interface ParamFlowRuleMapperExt extends ParamFlowRuleMapper{

    /**
     *热点规则分页查询接口
     * @param paramFlowRulePaginationQuery 入参
     * @return 结果
     */
    List<ParamFlowRule> selectParamFlowRuleWithPaging(ParamFlowRulePaginationQuery paramFlowRulePaginationQuery);

    /**
     * 查询热点规则条数
     * @param paramFlowRulePaginationQuery 入参
     * @return 条数
     */
    Integer countParamFlowRuleWithPaging(ParamFlowRulePaginationQuery paramFlowRulePaginationQuery);
}
