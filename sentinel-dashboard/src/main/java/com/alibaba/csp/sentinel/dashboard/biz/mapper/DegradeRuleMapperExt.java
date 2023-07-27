package com.alibaba.csp.sentinel.dashboard.biz.mapper;

import com.alibaba.csp.sentinel.dashboard.biz.domain.query.DegradeRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.DegradeRule;

import java.util.List;

public interface DegradeRuleMapperExt extends DegradeRuleMapper{

    /**
     * 熔断规则规则分页查询接口
     * @param degradeRulePaginationQuery 入参
     * @return 结果
     */
    List<DegradeRule> selectDegradeRuleWithPaging(DegradeRulePaginationQuery degradeRulePaginationQuery);

    /**
     * 查询条数
     * @param degradeRulePaginationQuery 入参
     * @return 条数
     */
    Integer countDegradeRuleWithPaging(DegradeRulePaginationQuery degradeRulePaginationQuery);
}
