package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.AbstractTestCase;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class AuthorityRuleQueryServiceImplTest extends AbstractTestCase {
    @Resource
    AuthorityRuleQueryService authorityRuleQueryService;
    @Test
    public void getAuthorityRuleEntityListByApp(){
        String app="ZookeeperFooProviderBootstrapV2";
        List<AuthorityRuleEntity> authorityRuleEntityList = authorityRuleQueryService.getAuthorityRuleEntityListByApp(app);
        System.out.println(authorityRuleEntityList);
    }
}
