package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.AbstractTestCase;
import org.junit.Test;

import javax.annotation.Resource;

public class FlowRuleConfigServiceImplTest extends AbstractTestCase {
    @Resource
    FlowRuleConfigService flowRuleConfigService;

    @Test
    public void updateConfigCenterRuleFromDbBatch(){
        flowRuleConfigService.updateConfigCenterRuleFromDbBatch();
    }
}
