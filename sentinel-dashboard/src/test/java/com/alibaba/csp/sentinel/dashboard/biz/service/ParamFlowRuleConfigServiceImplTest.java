package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.AbstractTestCase;
import org.junit.Test;

import javax.annotation.Resource;

public class ParamFlowRuleConfigServiceImplTest extends AbstractTestCase {
    @Resource
    ParamFlowRuleConfigService paramFlowRuleConfigService;

    @Test
    public void updateConfigCenterRuleFromDbBatch(){
        paramFlowRuleConfigService.updateConfigCenterRuleFromDbBatch();
    }
}
