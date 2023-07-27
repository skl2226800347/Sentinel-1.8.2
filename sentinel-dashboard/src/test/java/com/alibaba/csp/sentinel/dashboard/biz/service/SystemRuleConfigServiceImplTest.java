package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.AbstractTestCase;
import org.junit.Test;

import javax.annotation.Resource;

public class SystemRuleConfigServiceImplTest extends AbstractTestCase {
    @Resource
    SystemRuleConfigService systemRuleConfigService;
    @Test
    public void updateConfigCenterRuleFromDbBatch(){
        systemRuleConfigService.updateConfigCenterRuleFromDbBatch();
    }
}
