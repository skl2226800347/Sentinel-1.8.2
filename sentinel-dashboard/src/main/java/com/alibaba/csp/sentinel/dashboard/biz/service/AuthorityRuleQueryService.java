package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.AuthorityRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.AuthorityRule;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;

import java.util.List;

public interface AuthorityRuleQueryService {
    Pagination<AuthorityRule> queryAuthorityRuleListWithPaging(AuthorityRulePaginationQuery authorityRulePaginationQuery);

    List<AuthorityRuleEntity> getAuthorityRuleEntityListByApp(String app);

}
