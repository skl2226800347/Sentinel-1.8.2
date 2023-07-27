package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.AbstractTestCase;
import org.junit.Test;

import javax.annotation.Resource;

public class DegradeRuleConfigServiceImplTest extends AbstractTestCase {

    @Resource
    DegradeRuleConfigService degradeRuleConfigService;
    @Test
    public void updateConfigCenterRuleFromDbBatch(){
        degradeRuleConfigService.updateConfigCenterRuleFromDbBatch();
    }
}
