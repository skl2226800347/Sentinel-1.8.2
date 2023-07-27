package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.AbstractTestCase;
import org.junit.Test;

import javax.annotation.Resource;

public class AuthorityRuleConfigServiceImplTest extends AbstractTestCase {
    @Resource
    AuthorityRuleConfigService authorityRuleConfigService;

    @Test
    public void updateConfigCenterRuleFromDbBatch(){
        authorityRuleConfigService.updateConfigCenterRuleFromDbBatch();
    }
}
