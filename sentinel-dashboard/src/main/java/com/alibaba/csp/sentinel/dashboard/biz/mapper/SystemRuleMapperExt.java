package com.alibaba.csp.sentinel.dashboard.biz.mapper;

import com.alibaba.csp.sentinel.dashboard.biz.domain.query.SystemRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.SystemRule;

import java.util.List;

public interface SystemRuleMapperExt extends SystemRuleMapper{

    /**
     *系统保护规则分页查询接口
     * @param systemRulePaginationQuery 入参
     * @return 结果
     */
    List<SystemRule> selectSystemRuleWithPaging(SystemRulePaginationQuery systemRulePaginationQuery);

    /**
     * 查询系统保护规则条数
     * @param systemRulePaginationQuery 入参
     * @return 条数
     */
    Integer countSystemRuleWithPaging(SystemRulePaginationQuery systemRulePaginationQuery);
}
