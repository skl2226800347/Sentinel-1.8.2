package com.alibaba.csp.sentinel.dashboard.biz.mapper;

import com.alibaba.csp.sentinel.dashboard.biz.domain.query.AuthorityRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.AuthorityRule;

import java.util.List;

public interface AuthorityRuleMapperExt extends AuthorityRuleMapper{
    /**
     * 系统保护规则分页查询接口
     * @param authorityRulePaginationQuery 入参
     * @return 结果
     */
    List<AuthorityRule> selectAuthorityRuleWithPaging(AuthorityRulePaginationQuery authorityRulePaginationQuery);

    /**
     * 查询条数
     * @param authorityRulePaginationQuery 入参
     * @return 条数
     */
    Integer countAuthorityRuleWithPaging(AuthorityRulePaginationQuery authorityRulePaginationQuery);
}
