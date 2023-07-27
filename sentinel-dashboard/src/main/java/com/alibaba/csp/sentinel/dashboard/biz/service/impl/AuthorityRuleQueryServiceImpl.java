package com.alibaba.csp.sentinel.dashboard.biz.service.impl;

import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.AuthorityRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.AuthorityRule;
import com.alibaba.csp.sentinel.dashboard.biz.helper.AuthorityRuleHelper;
import com.alibaba.csp.sentinel.dashboard.biz.manager.AuthorityRuleQueryManager;
import com.alibaba.csp.sentinel.dashboard.biz.service.AuthorityRuleQueryService;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuthorityRuleQueryServiceImpl extends AbstractService implements AuthorityRuleQueryService {
    @Resource
    protected AuthorityRuleQueryManager authorityRuleQueryManager;


    @Override
    public Pagination<AuthorityRule> queryAuthorityRuleListWithPaging(AuthorityRulePaginationQuery authorityRulePaginationQuery) {
        Pagination<AuthorityRule> pagination =  authorityRuleQueryManager.queryAuthorityRuleListWithPaging(authorityRulePaginationQuery);
        return pagination;
    }


    @Override
    public List<AuthorityRuleEntity> getAuthorityRuleEntityListByApp(String app) {
        List<AuthorityRule> authorityRuleList = authorityRuleQueryManager.getAuthorityRuleByApp(app);
        List<AuthorityRuleEntity> authorityRuleEntityList = AuthorityRuleHelper.convertToAuthorityRuleEntity(authorityRuleList);
        return authorityRuleEntityList;
    }
}
