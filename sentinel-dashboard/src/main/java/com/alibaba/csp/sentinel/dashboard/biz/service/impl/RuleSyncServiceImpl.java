package com.alibaba.csp.sentinel.dashboard.biz.service.impl;

import com.alibaba.csp.sentinel.dashboard.biz.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RuleSyncServiceImpl implements RuleSyncService {
    private static final Logger logger = LoggerFactory.getLogger(RuleSyncServiceImpl.class);
    @Resource
    AuthorityRuleConfigService authorityRuleConfigService;
    @Resource
    DegradeRuleConfigService degradeRuleConfigService;
    @Resource
    FlowRuleConfigService flowRuleConfigService;
    @Resource
    ParamFlowRuleConfigService paramFlowRuleConfigService;
    @Resource
    SystemRuleConfigService systemRuleConfigService;

    @Override
    public void updateConfigCenterRuleFromDbBatch() {
        authorityRuleConfigService.updateConfigCenterRuleFromDbBatch();
        degradeRuleConfigService.updateConfigCenterRuleFromDbBatch();
        flowRuleConfigService.updateConfigCenterRuleFromDbBatch();
        paramFlowRuleConfigService.updateConfigCenterRuleFromDbBatch();
        systemRuleConfigService.updateConfigCenterRuleFromDbBatch();
    }
}
